package com.example.mytaskapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<TaskModel>> allTasks;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }
    public void insert(TaskModel task) {
        executor.execute(() -> taskDao.insert(task));
    }

    public void update(TaskModel task) {
        executor.execute(() -> taskDao.update(task));
    }

    public void delete(TaskModel task) {
        executor.execute(() -> taskDao.delete(task));
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }

}
