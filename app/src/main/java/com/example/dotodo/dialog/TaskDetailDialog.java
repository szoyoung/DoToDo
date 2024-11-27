package com.example.dotodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dotodo.R;
import com.example.dotodo.data.model.Task;
import com.example.dotodo.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;

public class TaskDetailDialog extends Dialog {
    private Task task;
    private TaskViewModel viewModel;
    private EditText descriptionEdit;
    private RadioGroup priorityGroup;
    private DatePicker deadlinePicker;
    private Context context;

    public TaskDetailDialog(Context context, Task task, TaskViewModel viewModel) {
        super(context);
        this.context = context;
        this.task = task;
        this.viewModel = viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_task_detail);

        // UI 요소 초기화
        descriptionEdit = findViewById(R.id.edit_description);
        priorityGroup = findViewById(R.id.priority_group);
        deadlinePicker = findViewById(R.id.deadline_picker);
        Button saveButton = findViewById(R.id.btn_save);
        Button cancelButton = findViewById(R.id.btn_cancel);

        // 현재 데이터 표시
        if (task.getDescription() != null) {
            descriptionEdit.setText(task.getDescription());
        }

        // 우선순위 설정
        RadioButton priorityButton = (RadioButton) priorityGroup.getChildAt(task.getPriority());
        if (priorityButton != null) {
            priorityButton.setChecked(true);
        }

        // 기한 설정
        if (task.getDeadline() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(task.getDeadline());
            deadlinePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        // 저장 버튼 클릭 리스너
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
                dismiss();
            }
        });

        // 취소 버튼 클릭 리스너
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void saveTask() {
        // 설명 업데이트
        task.setDescription(descriptionEdit.getText().toString().trim());

        // 우선순위 업데이트
        int selectedPriorityId = priorityGroup.getCheckedRadioButtonId();
        int priorityIndex = 0; // 기본값: 낮음
        if (selectedPriorityId == R.id.radio_medium) {
            priorityIndex = 1;
        } else if (selectedPriorityId == R.id.radio_high) {
            priorityIndex = 2;
        }
        task.setPriority(priorityIndex);

        // 기한 업데이트
        Calendar calendar = Calendar.getInstance();
        calendar.clear(); // 기존 시간 정보를 초기화
        calendar.set(
                deadlinePicker.getYear(),
                deadlinePicker.getMonth(),
                deadlinePicker.getDayOfMonth(),
                23, 59, 59  // 선택한 날짜의 마지막 시간으로 설정
        );
        task.setDeadline(calendar.getTime());

        // 데이터베이스 업데이트
        viewModel.update(task);
    }
}