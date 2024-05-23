package com.example.taskmanager.models;

public class Task {
    private String id;
    private String name;
    private String description;
    private String dueDate;
    private boolean complete;
    private String uid;

    public Task() {
    }

    public Task(String id, String name, String description, String dueDate, boolean complete, String uid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.complete = complete;
        this.uid = uid;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}