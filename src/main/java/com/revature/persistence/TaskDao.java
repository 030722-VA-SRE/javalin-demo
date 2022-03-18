package com.revature.persistence;

import java.time.LocalDate;
import java.util.List;

import com.revature.models.Task;

public interface TaskDao {
	public List<Task> getAllTasks();
	public List<Task> getTasksByCompletion(boolean isCompleted);
	public List<Task> getTasksByDueDate(LocalDate dueDate);
	public List<Task> getTasksByCompletionAndDueDate(boolean isCompleted, LocalDate dueDate);
	public Task getTaskById(int id);
	public int createTask(Task task);
	public boolean updateTask(Task task);
	public boolean deleteTaskById(int id);
}
