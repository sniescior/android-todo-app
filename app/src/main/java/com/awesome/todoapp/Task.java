package com.awesome.todoapp;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private Boolean done;
    private Category category;

    public Task() {
        this.id = UUID.randomUUID();
        this.date = new Date();
        this.category = Category.HOME;
        this.done = false;
    }

    public UUID getID() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return this.date;
    }

    public Boolean isDone() {
        return this.done;
    }

    public void setDone(Boolean checked) {
        this.done = checked;
    }

    public void setDate(Date time) {
        this.date = time;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }
}
