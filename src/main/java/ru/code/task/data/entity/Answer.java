package ru.code.task.data.entity;

import ru.code.task.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Answer {

    @Id
    private String id;
    private String value;

    public Answer() {
    }

    public Answer(String value) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
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

    @Override
    public String toString() {
        return value;
    }
}
