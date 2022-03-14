package com.revature.persitence;

import java.time.LocalDate;
import java.util.ArrayList;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.models.Task;

public class TaskArrayList implements TaskDao{

	/*
	 * Functionality: - retrieve all records - retrieve a record of a specific id -
	 * add a record - delete a record based on an id - update a record based on an
	 * id
	 */

	// mimicking persistence:
	private ArrayList<Task> tasks = new ArrayList<>();

	public TaskArrayList() {
		super();
		for (int i = 0; i < 5; i++) {
			Task t = new Task();
			t.setId(Task.taskCounter);
			t.setName("Task number: " + Task.taskCounter);
			t.setDueDate(LocalDate.now().plusDays(Task.taskCounter));
			tasks.add(t);
		}
	}

	// used by GET - /tasks/{id}
	public Task getTaskById(int id) {
		Task t = null;
		for(Task task :tasks) {
			if(task.getId() == id) {
				t = task;
				break;
			}
		}
		return t;
	}
	
	// used by GET - /tasks
	public ArrayList<Task> getTasks(){
		return tasks;
	}
	
	public int addTask(Task newTask) {
		boolean alreadyExists = false;
		// iterating tasks of task ArrayList and comparing tasks' names with the new task, if they match set already exists to true
		for(Task task : tasks) {
			if(newTask.getName().equals(task.getName())) {
				alreadyExists = true;
				break;
			}
		}
		
		if(alreadyExists) {
			return -1;
			// this would be an id that doesn't exist to indicate that the operation failed
		}else {
			newTask.setId(Task.taskCounter);
			tasks.add(newTask);
			return Task.taskCounter;
			// return the generated id
		}
	}
	
	public boolean deleteTask(int taskId) {
		boolean deletedSomething = false;
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getId() == taskId) {
				// if a task of that id is found, remove task from the arrayList
				tasks.remove(i);
				deletedSomething =true;
				}
		}
		return deletedSomething;
	}
}
