package ru.code.task.data.entity;

import ru.code.task.data.AbstractEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Questionnaire {

    @Id
    private String id;
    private String name;
    private String description;
    private long size;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Question> questions;

    public Questionnaire() {
    }

    public Questionnaire(String name, String description, List<Question> questions) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.questions = questions;
        this.size = questions.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", questions=" + questions +
                '}';
    }
}
