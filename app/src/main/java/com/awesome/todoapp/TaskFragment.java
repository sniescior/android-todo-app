package com.awesome.todoapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;
import java.util.logging.Logger;

public class TaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "ARG_TASK_ID";
    private Task task;
    private TextView taskNameText;
    private Button dateButton;
    private CheckBox taskDoneCheckbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        task = TaskStorage.getInstance().getTask(taskId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        taskNameText = view.findViewById(R.id.task_name);
        dateButton =  view.findViewById(R.id.task_date_button);
        taskDoneCheckbox = view.findViewById(R.id.task_done_checkbox);

        dateButton.setText(task.getDate().toString());
        dateButton.setEnabled(false);

        taskNameText.setText(task.getName());
        taskDoneCheckbox.setChecked(task.isDone());
        taskDoneCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
        });

        taskNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                task.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    public static TaskFragment newInstance(UUID taskID){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TASK_ID, taskID);
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }
}
