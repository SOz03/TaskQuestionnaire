package ru.code.task.views.completequestionnaire;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import ru.code.task.data.entity.*;
import ru.code.task.data.service.QuestionnaireService;
import ru.code.task.data.service.ResultAnswerService;
import ru.code.task.data.service.ResultService;
import ru.code.task.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;

import javax.annotation.security.PermitAll;
import java.text.SimpleDateFormat;
import java.util.*;

@PageTitle("Заполнить анкету")
@Route(value = "questionnaire", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class CompleteQuestionnaireView extends Div {

    private Button runQuestionnaireButton = new Button();
    private Button completeQuestionnaireButton = new Button();
    private Button refresh = new Button();
    private Tab tab1;
    private Tab tab2;
    private Span badge1;
    private Span badge2;
    private VerticalLayout layout1;
    private VerticalLayout layout2;
    private VerticalLayout verticalLayoutPage2;
    private HorizontalLayout buttonLayout;
    private Tabs tabs;
    private Div pages;
    private Div page1;
    private Div page2;
    private Label anketaLabel = new Label();
    private TextArea descriptionArea = new TextArea("Описание");

    private QuestionnaireService questionnaireService;
    private ResultAnswerService resultAnswerService;
    private ResultService resultService;

    private Grid<Questionnaire> grid;
    private Questionnaire activeQuestionnaireForRun;
    private List<Questionnaire> personList;
    List<Question> questionsListQuestionnaire;
    private Map<String, RadioButtonGroup> valueRadioHashMap;
    private Map<String, CheckboxGroup> valueCheckboxHashMap;

    public CompleteQuestionnaireView(@Autowired QuestionnaireService questionnaireService,
                                     @Autowired ResultAnswerService resultAnswerService,
                                     @Autowired ResultService resultService) {

        this.questionnaireService = questionnaireService;
        this.resultAnswerService = resultAnswerService;
        this.resultService = resultService;

        tabOne();
        tabTwo();
        settingsButton();
        settingsFlow();

        add(tabs, pages);
    }

    private void settingsButton() {

        runQuestionnaireButton.addClickListener(event -> {
            if (activeQuestionnaireForRun != null) {

                tab1.setEnabled(false);
                tab2.setEnabled(true);
                tab2.setSelected(true);
                page1.setVisible(false);
                page2.setVisible(true);
                verticalLayoutPage2 = new VerticalLayout();
                page2.add(verticalLayoutPage2);
                Questionnaire questionnaireDB = questionnaireService.findByIdQuestionnaire(activeQuestionnaireForRun.getId());

                TextArea descriptionQuestionnaire = new TextArea();
                descriptionQuestionnaire.setValue(questionnaireDB.getDescription());
                descriptionQuestionnaire.getStyle().set("maxHeight", "150px");
                descriptionQuestionnaire.getStyle().set("maxWidth", "800px");
                descriptionQuestionnaire.setWidth("800px");
                descriptionQuestionnaire.setReadOnly(true);
                verticalLayoutPage2.add(new H1(questionnaireDB.getName()));
                verticalLayoutPage2.add(descriptionQuestionnaire);

                valueRadioHashMap = new HashMap<>();
                valueCheckboxHashMap = new HashMap<>();
                RadioButtonGroup<String> radioGroup;
                CheckboxGroup<String> checkboxGroup;
                questionsListQuestionnaire = questionnaireDB.getQuestions();

                for (int i = 0; i < questionsListQuestionnaire.size(); i++) {
                    Question questionQuestionnaire = questionsListQuestionnaire.get(i);
                    ArrayList<String> listAnswerForQuestionnaire;
                    if (questionQuestionnaire.getIsOne()) {
                        listAnswerForQuestionnaire = new ArrayList<>();
                        radioGroup = new RadioButtonGroup<>();
                        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
                        radioGroup.setLabel(i + 1 + ". " + questionQuestionnaire.getName());
                        for (Answer answerQuestionnaire : questionQuestionnaire.getListAnswer()) {
                            listAnswerForQuestionnaire.add(answerQuestionnaire.getValue());
                        }
                        radioGroup.setItems(listAnswerForQuestionnaire);
                        valueRadioHashMap.put(questionQuestionnaire.getName(), radioGroup);
                        verticalLayoutPage2.add(radioGroup);
                    } else {
                        listAnswerForQuestionnaire = new ArrayList<>();
                        checkboxGroup = new CheckboxGroup<>();
                        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
                        checkboxGroup.setLabel(i + 1 + ". " + questionQuestionnaire.getName());
                        for (Answer answerQuestionnaire : questionQuestionnaire.getListAnswer()) {
                            listAnswerForQuestionnaire.add(answerQuestionnaire.getValue());
                        }
                        checkboxGroup.setItems(listAnswerForQuestionnaire);
                        valueCheckboxHashMap.put(questionQuestionnaire.getName(), checkboxGroup);
                        verticalLayoutPage2.add(checkboxGroup);
                    }
                }
                page2.add(completeQuestionnaireButton);
            } else {
                openMessage("Сначала выберете анкету.");
            }
        });

        completeQuestionnaireButton.addClickListener(event -> {
            tab1.setEnabled(true);
            tab2.setEnabled(false);
            tab1.setSelected(true);
            page1.setVisible(true);
            page2.setVisible(false);
            Locale.setDefault(Locale.getDefault());

            if (examination()) {
                boolean bln = false;
                try {
                    Result result = new Result(activeQuestionnaireForRun.getName(), new SimpleDateFormat().format(new Date()));
                    List<ResultAnswer> resultAnswers = new ArrayList<>();
                    for (RadioButtonGroup radioButtonGroup : valueRadioHashMap.values()){
                        ResultAnswer resultAnswer = new ResultAnswer(
                                radioButtonGroup.getLabel(),
                                radioButtonGroup.getValue().toString(),
                                result);
                        resultAnswers.add(resultAnswer);
                    }
                    for (CheckboxGroup checkboxGroup : valueCheckboxHashMap.values()){
                        ResultAnswer resultAnswer = new ResultAnswer(
                                checkboxGroup.getLabel(),
                                checkboxGroup.getValue().toString(),
                                result);
                        resultAnswers.add(resultAnswer);
                    }
                    resultService.update(result);
                    for (ResultAnswer res : resultAnswers) {
                        resultAnswerService.update(res);
                    }
                    bln = true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    openMessage("Ошибка базы данных: не удалось добавить сущности");
                }
                if (bln) {
                    Notification.show("Анкета успешно заполнена и добавлена в архив");

                    verticalLayoutPage2.removeAll();
                    page2.remove(verticalLayoutPage2);
                    page2.remove(completeQuestionnaireButton);
                    valueRadioHashMap.clear();
                }
            } else {
                openMessage("Заполните все вопросы для продолжения");
            }
        });

        refresh.addClickListener(event -> {
            personList = questionnaireService.findAll();
            grid.setItems(personList);
            descriptionArea.clear();
            Notification.show("Список обновлен");
        });

        grid.addItemClickListener(item -> {

            Questionnaire questionnaire = item.getItem();

            if (questionnaire != null && activeQuestionnaireForRun != questionnaire) {

                activeQuestionnaireForRun = questionnaire;
                descriptionArea.setVisible(true);
                StringBuilder sb = new StringBuilder();
                sb.append("Содержит следующие вопросы: \n");
                int count = 1;
                List<Question> questions = questionnaireService.findListQuestionsByIdQuestionnaire(questionnaire.getId());
                for (Question question : questions) {
                    sb.append("  ").append(+count).append(". ").append(question.getName()).append("\n");
                    count++;
                }
                descriptionArea.setValue(sb.toString());

            } else {
                activeQuestionnaireForRun = null;
                descriptionArea.setValue("");
            }
        });
    }

    private boolean examination() {
        boolean proceed = true;
        for (RadioButtonGroup radioButtonGroup : valueRadioHashMap.values()) {
            if (radioButtonGroup.getValue().equals("")) {
                proceed = false;
                break;
            }
        }
        for (CheckboxGroup checkboxGroup : valueCheckboxHashMap.values()) {
            if (checkboxGroup.getValue().equals("")) {
                proceed = false;
                break;
            }
        }
        return proceed;
    }

    private void settingsFlow() {
        addClassNames("questionnaire-designer-view", "flex", "flex-col"/*, "items-center"*/, "justify-center", "p-l",
                /* "text-center"*//*, "label-center"*/ "box-border");

        descriptionArea.getStyle().set("maxHeight", "200px");
        descriptionArea.getStyle().set("maxWidth", "2000px");
        descriptionArea.setWidth("1000px");
        descriptionArea.setReadOnly(true);

        tabs = new Tabs(tab1, tab2);
        pages = new Div(page1, page2);

    }

    private void tabOne() {
        runQuestionnaireButton.setText("Приступить к анкете");
        refresh.setIcon(new Icon(VaadinIcon.REFRESH));
        refresh.setHeight("40px");
        refresh.setWidth("20px");

        badge1 = new Span("Настройки");
        badge1.getStyle().set("fontSize", "75%");
        layout1 = new VerticalLayout(badge1, new Icon(VaadinIcon.COG));
        layout1.getStyle().set("alignItems", "center");
        tab1 = new Tab(layout1);

        page1 = new Div();
        page1.setWidth("3000px");
        page1.setWidthFull();
        anketaLabel.setText("Выберете из списка анкету, которую хотите пройти:");

        personList = questionnaireService.findAll();
        grid = new Grid<>(Questionnaire.class);
        grid.setMaxWidth("1500px");
        grid.setWidth("100%");
        grid.setItems(personList);
        grid.addColumn(Questionnaire::getName).setHeader("Название").setWidth("300px");
        grid.addColumn(Questionnaire::getDescription).setHeader("Описание").setWidth("450px");
        grid.addColumn(Questionnaire::getSize).setHeader("Кол-во вопросов").setWidth("100px");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("name");
        grid.removeColumnByKey("description");
        grid.removeColumnByKey("size");
        grid.removeColumnByKey("questions");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        buttonLayout = new HorizontalLayout();
        buttonLayout.add(runQuestionnaireButton);

        HorizontalLayout gridAndRefresh = new HorizontalLayout();
        gridAndRefresh.add(grid, refresh);

        page1.add(anketaLabel, gridAndRefresh, descriptionArea, buttonLayout);
    }

    private void tabTwo() {
        completeQuestionnaireButton.setText("Завершить анкету");

        badge2 = new Span("Анкета");
        badge2.getStyle().set("fontSize", "75%");
        layout2 = new VerticalLayout(badge2, new Icon(VaadinIcon.AREA_SELECT));
        layout2.getStyle().set("alignItems", "center");

        tab2 = new Tab(layout2);
        page2 = new Div();

        page2.setVisible(false);
        tab2.setEnabled(false);
    }

    private void openMessage(String message) {
        Notification notification = new Notification(message, 1500,
                Notification.Position.MIDDLE);
        notification.open();
    }

}
