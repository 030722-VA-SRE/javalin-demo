package com.revature.services;

import java.util.List;

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
		
		// additional business logic if needed
		return td.getAllTasks();
	}
	
	public List<Task> getTasksByCompletion(boolean isCompleted){
		return td.getTasksByCompletion(isCompleted);
	}
	
	public Task getTaskById(int id) {
		return td.getTaskById(id);
	}
	
	public boolean createTask(Task task) {
		int generatedId = td.createTask(task);
		if(generatedId != -1) {
			return true;
		}
		return false;
	}
	
	public boolean updateTask(Task task) {
		return td.updateTask(task);
	}
	
	public boolean deleteTaskById(int id) {
		return td.deleteTaskById(id);
	}
}
