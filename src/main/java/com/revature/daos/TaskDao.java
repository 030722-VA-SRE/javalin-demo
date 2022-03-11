package com.revature.daos;

import java.util.List;

import com.revature.models.Task;

/*-
 *  Our TaskDao interface is responsible for declaring method related to Tasks persistence
 *  	- In a collection
 *  	- In db..
 */

public interface TaskDao {

	/*- 
	 * CRUD
	 * 	- create a task
	 * 	- read/get a task from data persistence
	 * 		- get all tasks from the data persistence
	 * 	- update
	 * 	- delete
	 */
	
	// this method returns a unique identifier for a new record
	int addTask(Task task);
	// Task addTask(Task task); this returns the record with an assigned id
	Task getTask(int id);
	List<Task> getTasks();
	// returns a boolean based on the success of the update
	boolean updateTask(Task update);
	// returns a boolean based on the success of the deletion
	boolean deleteTask(int id);
}
