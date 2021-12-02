package ru.job4j.chat.model;

public abstract class Model {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
