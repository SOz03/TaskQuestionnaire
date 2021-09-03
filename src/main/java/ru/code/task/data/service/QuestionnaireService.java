package ru.code.task.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import ru.code.task.data.entity.Question;
import ru.code.task.data.entity.Questionnaire;
import ru.code.task.data.repository.QuestionnaireRepository;

import java.util.List;

@Service
public class QuestionnaireService  extends CrudService<Questionnaire, String> {
    private QuestionnaireRepository questionnaireRepository;

    public QuestionnaireService(@Autowired QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @Override
    protected QuestionnaireRepository getRepository() {
        return questionnaireRepository;
    }

    public List<Questionnaire> questionnaireList(){
        return questionnaireRepository.findAll();
    }

    public List<Question> findListByIdQuestionnaire(String id){
        return questionnaireRepository.findListByIdQuestionnaire(id);
    }
}
