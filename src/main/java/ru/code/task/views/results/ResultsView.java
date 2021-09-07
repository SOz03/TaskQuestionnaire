package ru.code.task.views.results;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import ru.code.task.data.entity.Questionnaire;
import ru.code.task.data.entity.Result;
import ru.code.task.data.entity.ResultAnswer;
import ru.code.task.data.service.QuestionnaireService;
import ru.code.task.data.service.ResultService;
import ru.code.task.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Результаты")
@Route(value = "results", layout = MainLayout.class)
@RolesAllowed("admin")
public class ResultsView extends Div {

    ComboBox<String> questionnaireNameComboBox = new ComboBox<>();
    Button generateListOfResults = new Button();
    Grid<Result> resultGrid = new Grid<>(Result.class);
    Grid<ResultAnswer> resultAnswerGrid = new Grid<>(ResultAnswer.class);

    private ResultService resultService;
    private QuestionnaireService questionnaireService;
    private String CONST_value = null;
    private List<ResultAnswer> active = null;

    public ResultsView(@Autowired ResultService resultService,
                       @Autowired QuestionnaireService questionnaireService) {

        this.resultService = resultService;
        this.questionnaireService = questionnaireService;
        settingsButton();
        settingsFlow();

    }

    private void settingsFlow() {
        addClassNames("questionnaire-designer-view", "flex", "flex-col", "justify-center", "p-l", "box-border");
        add(new H2("Результаты тестирования пользователей"));

        List<String> questionnaireName = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaireService.findAll()) {
            questionnaireName.add(questionnaire.getName());
        }
        questionnaireNameComboBox.setItems(questionnaireName);
        questionnaireNameComboBox.setPlaceholder("Выберете анкету из списка");
        generateListOfResults.setText("Сформировать результаты для текущей анкеты");

        resultGrid.removeColumnByKey("id");
        resultGrid.removeColumnByKey("nameQuestionnaire");
        resultGrid.removeColumnByKey("date");
        resultGrid.removeColumnByKey("resultAnswers");
        resultGrid.addColumn(Result::getNameQuestionnaire).setHeader("Анкета");
        resultGrid.addColumn(Result::getDate).setHeader("Дата");
        resultGrid.setVisible(false);

        resultAnswerGrid.removeColumnByKey("id");
        resultAnswerGrid.removeColumnByKey("questionTitle");
        resultAnswerGrid.removeColumnByKey("value");
        resultAnswerGrid.removeColumnByKey("result");
        resultAnswerGrid.addColumn(ResultAnswer::getQuestionTitle).setHeader("Вопрос");
        resultAnswerGrid.addColumn(ResultAnswer::getValue).setHeader("Ответ");
        resultAnswerGrid.setVisible(false);

        add(questionnaireNameComboBox, generateListOfResults, resultGrid, resultAnswerGrid);
    }

    private void settingsButton() {
        generateListOfResults.addClickListener(event -> {
            if (CONST_value == null) {
                Notification.show("Заполните поле");
            } else {
                resultGrid.setItems(resultService.findResultsByNameQuestionnaire(CONST_value));
                resultGrid.setVisible(true);
                resultAnswerGrid.setVisible(false);
            }
        });

        resultGrid.addItemClickListener(resultItemClickEvent -> {
            if (active == resultItemClickEvent.getItem().getResultAnswers()) {
                resultAnswerGrid.setVisible(false);
                active = null;
            } else {
                active = resultItemClickEvent.getItem().getResultAnswers();
                resultAnswerGrid.setItems(resultItemClickEvent.getItem().getResultAnswers());
                resultAnswerGrid.setVisible(true);
            }
        });

        questionnaireNameComboBox.addValueChangeListener(event -> {
            if (!event.getValue().equals(CONST_value)) {
                CONST_value = event.getValue();
                resultAnswerGrid.setVisible(false);
                resultGrid.setVisible(false);
            }
        });
    }
}
