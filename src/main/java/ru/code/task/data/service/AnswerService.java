package ru.code.task.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import ru.code.task.data.entity.Answer;
import ru.code.task.data.repository.AnswerRepository;

@Service
public class AnswerService extends CrudService<Answer, String> {
    private AnswerRepository answerRepository;

    public AnswerService(@Autowired AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    protected AnswerRepository getRepository() {
        return answerRepository;
    }
}
