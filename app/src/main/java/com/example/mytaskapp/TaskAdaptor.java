package com.example.mytaskapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdaptor extends RecyclerView.Adapter<TaskAdaptor.TaskViewHolder> {
    private List<TaskModel> taskList = new ArrayList<>();

    public TaskAdaptor() {
        this.taskList = taskList;
        notifyDataSetChanged();
    }
    public TaskModel getTaskAt(int position) {
        return taskList.get(position);
    }
    public void setTasks(List<TaskModel> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdaptor.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdaptor.TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.textViewTitle.setText(task.title);
        holder.textViewDescription.setText(task.description);
        holder.checkBox.setChecked(task.isCompleted);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.isCompleted = isChecked;
        });
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(task);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemLongClick(task);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();

    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;
        CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(TaskModel task);
        void onItemLongClick(TaskModel task);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}