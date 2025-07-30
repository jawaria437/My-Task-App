package com.example.mytaskapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TaskAdaptor adaptor = new TaskAdaptor();
        recyclerView.setAdapter(adaptor);
        adaptor.setOnItemClickListener(new TaskAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(TaskModel task) {
                showEditDialog(task);
            }

            @Override
            public void onItemLongClick(TaskModel task) {
                showDeleteConfirmation(task);
            }
        });

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, adaptor::setTasks);

        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> showAddDialog());

    }

    private void showAddDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.add_task_dialogue, null);
        EditText editTitle = dialogView.findViewById(R.id.editTaskTitle);
        EditText editDescription = dialogView.findViewById(R.id.editTaskDescription);
        CheckBox checkBoxCompleted = dialogView.findViewById(R.id.checkboxCompleted);
        new AlertDialog.Builder(this)
                .setTitle("New Task")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = editTitle.getText().toString().trim();
                    String description = editDescription.getText().toString().trim();
                    boolean completed = checkBoxCompleted.isChecked();

                    if (!title.isEmpty()) {
                        taskViewModel.insert(new TaskModel(title, description, completed));
                    } else {
                        Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void showEditDialog(TaskModel task) {
        View dialogView = getLayoutInflater().inflate(R.layout.add_task_dialogue, null);
        EditText editTitle = dialogView.findViewById(R.id.editTaskTitle);
        EditText editDescription = dialogView.findViewById(R.id.editTaskDescription);
        CheckBox checkBoxCompleted = dialogView.findViewById(R.id.checkboxCompleted);

        editTitle.setText(task.title);
        editDescription.setText(task.description);
        checkBoxCompleted.setChecked(task.isCompleted);

        new AlertDialog.Builder(this)
                .setTitle("Edit Task")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newTitle = editTitle.getText().toString().trim();
                    String newDescription = editDescription.getText().toString().trim();
                    boolean isCompleted = checkBoxCompleted.isChecked();

                    if (!newTitle.isEmpty()) {
                        task.title = newTitle;
                        task.description = newDescription;
                        task.isCompleted = isCompleted;
                        taskViewModel.update(task);
                    } else {
                        Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void showDeleteConfirmation(TaskModel task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> taskViewModel.delete(task))
                .setNegativeButton("Cancel", null)
                .show();
    }
}