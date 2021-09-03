package ru.code.task.views.questionnairedesigner;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import ru.code.task.data.entity.Answer;
import ru.code.task.data.entity.Question;
import ru.code.task.data.entity.Questionnaire;
import ru.code.task.data.service.AnswerService;
import ru.code.task.data.service.QuestionService;
import ru.code.task.data.service.QuestionnaireService;
import ru.code.task.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@PageTitle("Конструктор анкет")
@Route(value = "constructor", layout = MainLayout.class)
@RolesAllowed("admin")
public class QuestionnaireDesignerView extends Div {

    private final QuestionnaireService questionnaireService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    Button addQuestionButton = new Button();
    Button complete = new Button();
    TextField nameQuestionnaire = new TextField();
    TextField nameQuestion = new TextField();
    TextField answer1 = new TextField();
    TextField answer2 = new TextField();
    TextField answer3 = new TextField();
    TextField answer4 = new TextField();
    TextField answer5 = new TextField();
    TextField answer6 = new TextField();
    TextField answer7 = new TextField();
    TextField answer8 = new TextField();

    Label label = new Label();
    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout1 = new HorizontalLayout();
    HorizontalLayout horizontalLayout2 = new HorizontalLayout();
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    Grid<Question> grid = new Grid<>();
    TextArea textArea = new TextArea("Описание анкеты");
    private TextArea questionText = new TextArea("Описание вопроса");

    private List<Question> CONST_questions;
    private List<Question> questions;
    private List<Answer> CONST_answers;
    private List<Answer> answers = new ArrayList<>();
    private boolean selected;
    private Questionnaire anketa;

    public QuestionnaireDesignerView(@Autowired AnswerService answerService,
                                     @Autowired QuestionnaireService questionnaireService,
                                     @Autowired QuestionService questionService) {
        this.answerService = answerService;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;

        settingsFlow();
        activeButton();
        horizontalLayout1.add(answer1, answer2, answer3, answer4);
        horizontalLayout2.add(answer5, answer6, answer7, answer8);
        verticalLayout.add(nameQuestionnaire, textArea, nameQuestion, radioGroup, horizontalLayout1, horizontalLayout2, addQuestionButton);
        add(verticalLayout, grid, questionText, complete);
    }

    private void settingsFlow() {
        addClassNames("questionnaire-designer-view", "flex", "flex-col", "items-center", "justify-center", "p-l",
                "text-center", "label-center", "box-border");

        questions = new ArrayList<>();
        CONST_questions = new ArrayList<>();
        CONST_answers = new ArrayList<>();

        Div wrapper = new Div();
        wrapper.addClassNames("box-border");
        Style wrapperStyle = wrapper.getStyle();
        wrapperStyle.set("padding-top", "34px");
        wrapperStyle.set("border-radius", "100px");
        wrapperStyle.set("background", "var(--lumo-shade-10pct)");

        textArea.getStyle().set("maxHeight", "150px");
        textArea.getStyle().set("maxWidth", "500px");
        textArea.setWidth("500");
        textArea.setPlaceholder("Введите здесь что-то для описания анкеты");

        questionText.getStyle().set("maxHeight", "200px");
        questionText.getStyle().set("maxWidth", "2000px");
        questionText.setWidth("1000px");
        questionText.setReadOnly(true);

        questionText.setVisible(false);
        nameQuestionnaire.setLabel("Укажите название анкеты");
        nameQuestion.setLabel("Укажите вопрос");
        label.setText("Укажите варианты ответа");
        radioGroup.setLabel("Выберите тип вопроса");
        radioGroup.setItems("Одиночный выбор", "Множественный выбор");
        radioGroup.setHelperText("Здесь вы можете выбрать количество ответов на вопрос");
        radioGroup.setValue("Одиночный выбор");
        addQuestionButton.setText("Добавить вопрос в анкету");
        complete.setText("Завершить анкету");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.addColumn(Question::getName).setHeader("Вопрос");
        grid.addColumn(Question::getIsOne).setHeader("Один.выбор");

        answer1.setClearButtonVisible(true);
        answer1.setPlaceholder("Вариант 1");
        answer2.setClearButtonVisible(true);
        answer2.setPlaceholder("Вариант 2");
        answer3.setClearButtonVisible(true);
        answer3.setPlaceholder("Вариант 3");
        answer4.setClearButtonVisible(true);
        answer4.setPlaceholder("Вариант 4");
        answer5.setClearButtonVisible(true);
        answer5.setPlaceholder("Вариант 5");
        answer6.setClearButtonVisible(true);
        answer6.setPlaceholder("Вариант 6");
        answer7.setClearButtonVisible(true);
        answer7.setPlaceholder("Вариант 7");
        answer8.setClearButtonVisible(true);
        answer8.setPlaceholder("Вариант 8");
        nameQuestionnaire.setClearButtonVisible(true);
        nameQuestion.setClearButtonVisible(true);
    }

    private void activeButton() {
        addQuestionButton.addClickListener(event -> {

            if (!nameQuestion.getValue().equals("")) {
                if (isClear()) {
                    selected = radioGroup.getValue().equals("Одиночный выбор");
                    Question question = new Question(nameQuestion.getValue(), selected, answers);

                    questions.add(question);
                    CONST_questions.add(question);
                    grid.setItems(questions);

                    answers = new ArrayList<>();
                    clearTextField();

                    Notification.show("Вопрос добавлен в анкету.");
                } else {
                    openMessage("Заполните 2 или более варианта ответа");
                    answers.clear();
                }
            } else {
                openMessage("Заполните название вопроса");
            }
        });

        complete.addClickListener(event -> {
            if(!nameQuestionnaire.getValue().equals("")){
                if(questions.size() != 0){
                    anketa = new Questionnaire(nameQuestionnaire.getValue(), textArea.getValue(), questions);
                    if(updateDB()){
                        nameQuestionnaire.setValue("");
                        clearTextField();
                        answers.clear();
                        questions.clear();
                        textArea.clear();
                        grid.setItems(questions);

                        CONST_answers.clear();
                        CONST_questions.clear();
                    } else {
                        openMessage("Ошибка на сервере! БД не работает");
                    }
                } else {
                    openMessage("Не хватает вопросов для создания анкеты");
                }
            } else {
                openMessage("Заполните название вашей анкеты!");
            }
        });

        grid.addItemClickListener(questionItemClickEvent -> {
            Question selectQuestion = questionItemClickEvent.getItem();
            if (selectQuestion != null) {
                questionText.setVisible(true);
                StringBuilder sb = new StringBuilder();
                sb.append("Вопрос - ").append(selectQuestion.getName()).append(":\n");
                int count = 1;
                List<Answer> listAnswer = selectQuestion.getListAnswer();
                for (Answer answer: listAnswer){
                    sb.
                            append("  ").
                            append(count).
                            append(") ").
                            append(answer.getValue()).
                            append("\n");
                    count++;
                }
                questionText.setValue(sb.toString());
            }
        });

    }

    private boolean updateDB(){
        boolean value = false;
        try{
            for(Answer answer: CONST_answers){
                answerService.update(answer);
            }
            for(Question question: CONST_questions){
                questionService.update(question);
            }
            questionnaireService.update(anketa);
            value = true;
        }catch (Exception exception){
            exception.printStackTrace();
            System.err.println("ОШИБКА добавления сущностей в бд");
        }
        return value;
    }

    private void openMessage(String message) {
        Notification notification = new Notification(message, 1500,
                Notification.Position.MIDDLE);
        notification.open();
    }

    private int getCount(TextField textField) {
        if (!textField.getValue().equals("")) {
            Answer answer = new Answer(textField.getValue());
            answers.add(answer);
            CONST_answers.add(answer);
            return 1;
        }
        return 0;
    }
    
    private void clearTextField(){
        questionText.setValue("");
        nameQuestion.setValue("");
        answer1.clear();
        answer2.clear();
        answer3.clear();
        answer4.clear();
        answer5.clear();
        answer6.clear();
        answer7.clear();
        answer8.clear();
    }

    private boolean isClear() {
        int count = getCount(answer1) + getCount(answer2) + getCount(answer3) +
                getCount(answer4) + getCount(answer5) + getCount(answer6) +
                getCount(answer7) + getCount(answer8);

        return count >= 2;
    }
}
