package com.revature.persitence;

import java.util.List;

import com.revature.models.Task;

public interface TaskDao {
	/*-
	 * Functionality: 
	 * 	- retrieve all records 
	 * 	- retrieve a record of a specific id 
	 * 	- add a record 
	 * 	- delete a record based on an id 
	 * 	- update a record based on an id
	 */
	
	public Task getTaskById(int id);
	public List<Task> getTasks();
	public int addTask(Task newTask);
	public boolean deleteTask(int id);
}
