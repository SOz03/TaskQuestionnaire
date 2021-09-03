package ru.code.task.data.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Question{

    @Id
    private String id;
    private String name;
    private boolean isOne;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Answer> listAnswer;

    public Question() {
    }

    public Question(String name, boolean isOne, List<Answer> listAnswer) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.isOne = isOne;
        this.listAnswer = listAnswer;
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

    public boolean getIsOne() {
        return isOne;
    }

    public void setOne(boolean isOne) {
        this.isOne = isOne;
    }

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }

    @Override
    public String toString() {
        return "Вопрос '" +  name + "' с одиночным выбором - " + isOne +
                ", списком ответов: " + listAnswer;
    }

}
