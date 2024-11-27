package com.example.dotodo.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.dotodo.data.model.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY completed ASC, priority DESC")
    LiveData<List<Task>> getAllTasks();

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}