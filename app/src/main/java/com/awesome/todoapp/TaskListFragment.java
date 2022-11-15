package com.awesome.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListFragment extends Fragment {
    public static final String KEY_EXTRA_TASK_ID = "KEY_EXTRA_TASK_ID";
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private boolean subtitleVisible;
    public static final String KEY_SUBTITLE = "subtitle";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null) {
            subtitleVisible = savedInstanceState.getBoolean(KEY_SUBTITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateView();
        return view;
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Task task;
        private TextView nameTextView;
        private TextView dateTextView;

        private CheckBox taskDoneCheckbox;
        private ImageView iconImageView;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            nameTextView = itemView.findViewById(R.id.task_item_name);
            dateTextView = itemView.findViewById(R.id.task_item_date);

            iconImageView = itemView.findViewById(R.id.task_category_img);
            taskDoneCheckbox = itemView.findViewById(R.id.task_done_ckb);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Task task){
            this.task = task;
            dateTextView.setText(task.getDate().toString());

            if(task.getName().length() > 10) {
                nameTextView.setText(task.getName().substring(0, 10) + "...");
            } else {
                nameTextView.setText(task.getName());
            }

            if(task.getCategory().equals(Category.HOME)) {
                iconImageView.setImageResource(R.drawable.ic_house);
            } else {
                iconImageView.setImageResource(R.drawable.ic_university);
            }

            if(task.isDone()) {
                nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                nameTextView.setPaintFlags(nameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getID());
            startActivity(intent);
        }

        public CheckBox getCheckBox() {
            return this.taskDoneCheckbox;
        }

        public TextView getTextView() {
            return nameTextView;
        }
    }

    private class TaskAdapter extends  RecyclerView.Adapter<TaskHolder>{
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks){
            this.tasks =tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = tasks.get(position);
            holder.bind(task);

            CheckBox checkBox = holder.getCheckBox();
            checkBox.setChecked(tasks.get(position).isDone());

            TextView nameTextView = holder.getTextView();

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tasks.get(holder.getAdapterPosition()).setDone(isChecked);

                if (tasks.get(holder.getAdapterPosition()).isDone()) {
                    nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    nameTextView.setPaintFlags(nameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            });
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }

    public void updateSubtitle() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();

        int toDoTasksCount = 0;

        for(Task task : tasks) {
            if(!task.isDone()) { toDoTasksCount++; }
        }
        String subtitle = getString(R.string.subtitle_format, toDoTasksCount);
        if(!subtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateView(){
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();
        if(adapter == null){
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onResume() {

        super.onResume();
        updateView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(subtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.new_task):
                Task task = new Task();
                TaskStorage.getInstance().addTask(task);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(TaskListFragment.KEY_EXTRA_TASK_ID, task.getID());
                startActivity(intent);
                return true;
            case (R.id.show_subtitle):
                subtitleVisible = !subtitleVisible;
                getActivity().invalidateOptionsMenu();

                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
