package com.revature.services;

import java.time.LocalDate;
import java.util.List;

import com.revature.exceptions.TaskNotFoundException;
import com.revature.models.Task;
import com.revature.persistence.TaskDao;
import com.revature.persistence.TaskPostgres;

public class TaskService {

	private TaskDao td;
	
	public TaskService() {
		// initializing relevant TaskDao implementation when Task service is instantiated
		td = new TaskPostgres();
	}
	
	public List<Task> getAllTasks(){
		return td.getAllTasks();
	}
	
	public List<Task> getTasksByCompletion(boolean isCompleted){
		return td.getTasksByCompletion(isCompleted);
	}
	
	public List<Task> getTasksByDueDate(LocalDate dueDate){
		return td.getTasksByDueDate(dueDate);
	}
	
	public List<Task> getTasksByCompletionAndDueDate(boolean isCompleted, LocalDate dueDate){
		return td.getTasksByCompletionAndDueDate(isCompleted, dueDate);
	}
	
	public Task getTaskById(int id) throws TaskNotFoundException {
		Task task = td.getTaskById(id);
		if(task == null) {
			throw new TaskNotFoundException();
		}
		return td.getTaskById(id);
	}
	
	public boolean createTask(Task task) {
		int generatedId = td.createTask(task);
		if(generatedId != -1) {
			return true;
		}
		return false;
	}
	
	// TODO: implement updateTask
	public boolean updateTask(Task task) {
		return false;
	}
	
	// TODO: implement deleteTaskById
	public boolean deleteTaskById(int id) {
		return false;
	}
}
