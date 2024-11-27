package com.example.dotodo;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotodo.adapter.TaskAdapter;
import com.example.dotodo.data.model.Task;
import com.example.dotodo.dialog.TaskDetailDialog;
import com.example.dotodo.viewmodel.TaskViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;
    private TextInputEditText editNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupViewModel();
        setupTaskInput();
    }

    private void initializeViews() {
        editNewTask = findViewById(R.id.edit_new_task);
        ImageButton btnAddTask = findViewById(R.id.btn_add_task);

        // 추가 버튼 클릭 리스너 설정
        btnAddTask.setOnClickListener(v -> addNewTask());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // 전체 목록 관찰
        taskViewModel.getAllTasks().observe(this, tasks -> adapter.submitList(tasks));

        // 개별 업데이트 관찰
        taskViewModel.getUpdatedTask().observe(this, task -> {
            if (task != null) {
                adapter.updateTask(task);
            }
        });
    }

    private void setupTaskInput() {
        editNewTask.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addNewTask();
                return true;
            }
            return false;
        });
    }

    private void addNewTask() {
        String title = editNewTask.getText().toString().trim();
        if (!title.isEmpty()) {
            Task task = new Task(title);
            task.setDeadline(new Date()); // 기본 날짜 설정
            taskViewModel.insert(task);
            editNewTask.setText("");
            // 키보드 숨기기
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editNewTask.getWindowToken(), 0);
        }
    }

    @Override
    public void onTaskClick(Task task) {
        task.setCompleted(!task.isCompleted());
        taskViewModel.update(task);
    }

    @Override
    public void onTaskLongClick(Task task) {
        TaskDetailDialog dialog = new TaskDetailDialog(this, task, taskViewModel);
        dialog.show();
    }

}