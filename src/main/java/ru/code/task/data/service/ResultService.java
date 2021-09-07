package ru.code.task.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import ru.code.task.data.entity.Result;
import ru.code.task.data.repository.ResultRepository;

import java.util.List;

@Service
public class ResultService extends CrudService<Result, String> {
    private ResultRepository resultRepository;

    public ResultService(@Autowired ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    protected ResultRepository getRepository() {
        return resultRepository;
    }

    public String findNameQuestionnaireById(String id){
        return resultRepository.findNameQuestionnaireById(id);
    }

    public List<Result> resultList(){
        return resultRepository.findAll();
    }

    public List<Result> findResultsByNameQuestionnaire(String nameQuestionnaire){
        return resultRepository.findResultsByNameQuestionnaire(nameQuestionnaire);
    }
}
