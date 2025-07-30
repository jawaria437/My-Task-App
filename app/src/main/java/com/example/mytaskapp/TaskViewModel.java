package com.example.mytaskapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<TaskModel>> allTasks;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }
    public void insert(TaskModel task) {
        repository.insert(task);
    }

    public void update(TaskModel task) {
        repository.update(task);
    }

    public void delete(TaskModel task) {
        repository.delete(task);
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }
}
