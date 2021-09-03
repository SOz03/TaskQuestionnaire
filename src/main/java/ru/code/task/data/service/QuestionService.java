package ru.code.task.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import ru.code.task.data.entity.Answer;
import ru.code.task.data.entity.Question;
import ru.code.task.data.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService  extends CrudService<Question, String> {
    private QuestionRepository questionRepository;

    public QuestionService(@Autowired QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    protected QuestionRepository getRepository() {
        return questionRepository;
    }

    public List<Answer> findListByIdQuestion(String id){
        return questionRepository.findListByIdQuestion(id);
    }
}
