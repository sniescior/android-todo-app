package com.awesome.todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {
    private static final TaskStorage taskStorage = new TaskStorage();

    private final List<Task> tasks;

    public static  TaskStorage getInstance() { return taskStorage; }

    public Task getTask(UUID taskID) {
        for(Task task: this.tasks) if(task.getID().equals(taskID)) return task;
        return null;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    private TaskStorage() {
        tasks = new ArrayList<>();
        for(int i = 1; i <= 150; i++) {
            Task task = new Task();
            task.setName("Pilne zadanie nr: " + i);
            task.setDone(i % 3 == 0);
            tasks.add(task);
        }
    }
}
