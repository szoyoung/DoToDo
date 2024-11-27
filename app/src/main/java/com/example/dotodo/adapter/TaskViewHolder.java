package com.example.dotodo.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotodo.R;
import com.example.dotodo.data.model.Task;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private CheckBox checkBox;
    private TextView titleView;
    private TextView deadlineView;
    private SimpleDateFormat dateFormat;

    public TaskViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox_task);
        titleView = itemView.findViewById(R.id.text_title);
        deadlineView = itemView.findViewById(R.id.text_deadline);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public void bind(Task task, TaskAdapter.OnTaskClickListener listener) {
        // 제목 설정
        titleView.setText(task.getTitle());

        // 체크박스 상태 설정
        checkBox.setChecked(task.isCompleted());

        // 마감일 표시
        if (task.getDeadline() != null) {
            deadlineView.setVisibility(View.VISIBLE);
            deadlineView.setText(dateFormat.format(task.getDeadline()));
        } else {
            deadlineView.setVisibility(View.GONE);
        }

        // 우선순위에 따른 배경색 설정 (optional)
        int backgroundColor;
        switch (task.getPriority()) {
            case 2: // 높음
                backgroundColor = itemView.getContext().getResources().getColor(R.color.priority_high);
                break;
            case 1: // 보통
                backgroundColor = itemView.getContext().getResources().getColor(R.color.priority_medium);
                break;
            default: // 낮음
                backgroundColor = itemView.getContext().getResources().getColor(R.color.priority_low);
                break;
        }
        itemView.setBackgroundColor(backgroundColor);

        // 클릭 리스너 설정
        checkBox.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(task);
            }
        });

        // 길게 누르기 리스너 설정
        itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onTaskLongClick(task);
                return true;
            }
            return false;
        });
    }
}