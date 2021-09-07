package ru.code.task.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class ResultAnswer {

    @Id
    private String id;
    private String questionTitle;
    private String value;

    @ManyToOne
    private Result result;

    public ResultAnswer(){
    }

    public ResultAnswer(String questionTitle, String value, Result result) {
        this.id = UUID.randomUUID().toString();
        this.questionTitle = questionTitle;
        this.value = value;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultAnswer{" +
                "id='" + id + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", value='" + value + '\'' +
                ", result=" + result +
                '}';
    }
}
