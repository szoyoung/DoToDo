package com.example.dotodo.util;

import com.example.dotodo.data.model.DaySchedule;
import com.example.dotodo.data.model.ScheduleEvent;
import com.example.dotodo.data.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleParser {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{2}:\\d{2})\\s*-\\s*(\\d{2}:\\d{2})\\s*:\\s*(.+)");

    public static String buildPrompt(List<Task> tasks, String fixedSchedule, Date startDate) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("다음 정보를 바탕으로 7일간의 일정을 만들어주세요.\n\n");

        // 할 일 목록 추가
        prompt.append("【할 일 목록】\n");
        for (Task task : tasks) {
            prompt.append(String.format("- %s (우선순위: %d, 마감일: %s)\n",
                    task.getTitle(),
                    task.getPriority(),
                    DATE_FORMAT.format(task.getDeadline())));
        }

        // 고정 스케줄 추가
        prompt.append("\n【고정 스케줄】\n").append(fixedSchedule);

        // 응답 형식 지정
        prompt.append("\n\n다음 형식으로 응답해주세요:\n");
        prompt.append("===\n");
        prompt.append("2024-12-07\n");
        prompt.append("09:00-10:30: 영어 공부\n");
        prompt.append("11:00-12:00: 과제 작성\n");
        prompt.append("===\n");
        prompt.append("각 날짜는 === 구분선으로 구분해주세요.");

        return prompt.toString();
    }

    public static List<DaySchedule> parseResponse(String response) {
        List<DaySchedule> weekSchedule = new ArrayList<>();
        String[] days = response.split("===");

        for (String day : days) {
            if (day.trim().isEmpty()) continue;

            String[] lines = day.trim().split("\n");
            if (lines.length < 1) continue;

            try {
                // 첫 줄은 날짜
                Date date = DATE_FORMAT.parse(lines[0].trim());
                DaySchedule daySchedule = new DaySchedule(date);

                // 나머지 줄들은 일정
                for (int i = 1; i < lines.length; i++) {
                    Matcher matcher = TIME_PATTERN.matcher(lines[i].trim());
                    if (matcher.find()) {
                        String startTime = matcher.group(1);
                        String endTime = matcher.group(2);
                        String title = matcher.group(3);

                        ScheduleEvent event = new ScheduleEvent(
                                startTime,
                                endTime,
                                title,
                                title.contains("고정") ? ScheduleEvent.EventType.FIXED : ScheduleEvent.EventType.TASK
                        );

                        daySchedule.addEvent(event);
                    }
                }

                daySchedule.sortEventsByTime();
                weekSchedule.add(daySchedule);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return weekSchedule;
    }
}