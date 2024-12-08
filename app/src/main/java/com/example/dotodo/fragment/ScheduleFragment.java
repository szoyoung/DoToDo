package com.example.dotodo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotodo.R;
import com.example.dotodo.adapter.WeeklyScheduleAdapter;
import com.example.dotodo.viewmodel.ScheduleViewModel;

public class ScheduleFragment extends Fragment {
    private ScheduleViewModel viewModel;
    private ProgressBar progressBar;
    private RecyclerView scheduleList;
    private WeeklyScheduleAdapter adapter;
    private Button createButton;
    private boolean isTasksLoaded = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 뷰 초기화
        initViews(view);
        // 뷰모델 설정
        setupViewModel();
        // 리사이클러뷰 설정
        setupRecyclerView();
        // 버튼 클릭 리스너 설정
        setupCreateButton();
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        scheduleList = view.findViewById(R.id.schedule_list);
        createButton = view.findViewById(R.id.btn_create_schedule);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        // Task 데이터 변화 관찰
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            isTasksLoaded = true;  // 데이터 로드 완료
            if(tasks != null) {
                Log.d("ScheduleFragment", "Tasks loaded: " + tasks.size());
            }
        });

        // 로딩 상태 관찰
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), this::setLoading);

        // 스케줄 데이터 관찰
        viewModel.getWeeklySchedule().observe(getViewLifecycleOwner(), schedules -> {
            adapter.setSchedules(schedules);
        });

        // 에러 메시지 관찰
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupCreateButton() {
        createButton.setOnClickListener(v -> {
            if (isTasksLoaded) {  // Task 데이터가 로드된 후에만 스케줄 생성 가능
                viewModel.generateSchedule();
            } else {
                Toast.makeText(requireContext(), "데이터 로딩 중입니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new WeeklyScheduleAdapter();
        scheduleList.setAdapter(adapter);
        scheduleList.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

//    private void setupCreateButton() {
//        createButton.setOnClickListener(v -> {
//            viewModel.generateSchedule();
//        });
//    }

    private void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        createButton.setEnabled(!isLoading);
    }
}