package ru.code.task.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import ru.code.task.data.entity.ResultAnswer;
import ru.code.task.data.repository.ResultAnswerRepository;

@Service
public class ResultAnswerService extends CrudService<ResultAnswer, String> {
    private ResultAnswerRepository resultAnswerRepository;

    public ResultAnswerService(@Autowired ResultAnswerRepository resultAnswerRepository) {
        this.resultAnswerRepository = resultAnswerRepository;
    }

    @Override
    protected ResultAnswerRepository getRepository() {
        return resultAnswerRepository;
    }
}
