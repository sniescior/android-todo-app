package com.awesome.todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {
    private static final TaskStorage taskStorage = new TaskStorage();

    private final List<Task> tasks;

    public static  TaskStorage getInstance() { return taskStorage; }

    public Task getTask(UUID taskID) {
        Task taskFound = new Task();
        for(Task task : tasks){
            if(task.getID().equals(taskID)) taskFound = task;
        }
        System.out.println("Found task: " + taskFound.getName());
        return taskFound;
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
            if (i % 3 == 0) {
                task.setCategory(Category.STUDIES);
            } else {
                task.setCategory(Category.HOME);
            }
            tasks.add(task);
        }
    }
}
