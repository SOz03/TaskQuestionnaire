package ru.code.task.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Answer {

    @Id
    private String id;
    private String value;

    @ManyToOne
    private Question question;

    public Answer() {
    }

    public Answer(String value, Question question) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return value;
    }
}
