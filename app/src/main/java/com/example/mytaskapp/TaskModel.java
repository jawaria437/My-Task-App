package com.example.mytaskapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")

public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public String title;
    public String description;
    public boolean isCompleted;

    public TaskModel(String title, String description, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
