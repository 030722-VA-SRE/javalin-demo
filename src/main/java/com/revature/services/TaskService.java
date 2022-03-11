package com.revature.services;

import com.revature.daos.TaskDao;
import com.revature.daos.TaskPostgres;
import com.revature.models.Task;

public class TaskService {

	private TaskDao td = new TaskPostgres();

	public Task createTask(Task task) {
		/*-
		 * Business logic can be applied here
		 * 	- every new task has default due date of +5 days
		 */
		int id = td.addTask(task);
		/*-
		 * if task sucessfully added
		 */
		if (id != -1) {
			task.setId(id);
			return task;
		}
		return null;
	}
}
