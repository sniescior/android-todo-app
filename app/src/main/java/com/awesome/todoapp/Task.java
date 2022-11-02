package com.awesome.todoapp;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private Boolean done;

    public Task() {
        this.id = UUID.randomUUID();
        this.date = new Date();
    }

    public UUID getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return date;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean checked) {
        this.done = checked;
    }
}
