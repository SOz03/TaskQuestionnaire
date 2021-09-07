package ru.code.task.data.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Result {

    @Id
    private String id;
    private String nameQuestionnaire;
    private String date;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "result")
    private List<ResultAnswer> resultAnswers;

    public Result() {
    }

    public Result(String nameQuestionnaire, String date) {
        this.id = UUID.randomUUID().toString();
        this.nameQuestionnaire = nameQuestionnaire;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameQuestionnaire() {
        return nameQuestionnaire;
    }

    public void setNameQuestionnaire(String nameQuestionnaire) {
        this.nameQuestionnaire = nameQuestionnaire;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ResultAnswer> getResultAnswers() {
        return resultAnswers;
    }

    public void setResultAnswers(List<ResultAnswer> resultAnswers) {
        this.resultAnswers = resultAnswers;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id='" + id + '\'' +
                ", nameQuestionnaire='" + nameQuestionnaire + '\'' +
                ", date=" + date +
                ", resultAnswers=" + resultAnswers +
                '}';
    }
}
