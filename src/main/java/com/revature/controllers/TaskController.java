package com.revature.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.exceptions.TaskNotFoundException;
import com.revature.models.Task;
import com.revature.services.TaskService;

import io.javalin.http.Context;

/*-
 * This class handles HTTP requests related to the Task model
 * 	- the HTTP requests are routed to these methods 
 */
public class TaskController {
/*-
 * The TaskService field allows the Task controller to leverage its methods to handle the requests
 */
	private static TaskService ts = new TaskService();
	
	/*- 
	 * The getTasks method handles all GET requests sent to /tasks including those with Query Params
	 * 		- ie: /tasks?dueDate=[...]
	 * 			- in this case, dueDate is the name of the query param and [...] is its value
	 * 		- to ensure the proper handling of the request, we have to determine whether a query param is passed or not
	 */
	public static void getTasks(Context ctx) {
		// returns the value of query param dueDate, or null if none is provided
		String dueDate = ctx.queryParam("dueDate");
		// returns the value of query param isCompleted, or null if none is provided
		String isCompleted = ctx.queryParam("isCompleted");
		
		/*-
		 * Depending on the presence of the query params, different response have to be sent
		 * 		- if dueDate and isCompleted are null
		 * 			- send all tasks
		 * 		- if dueDate has a value but not isCompleted
		 * 			- send tasks filtered by dueDate
		 * 		- if isCompleted has a value but not dueDate
		 * 			- send tasks filtered by isCompleted
		 * 		- if both have a value, filter by both
		 */
		if(dueDate == null && isCompleted == null) {
			ctx.json(ts.getAllTasks());
		} else if (dueDate != null && isCompleted == null) {
			// converting a string to a localdate can be a bit finicky, provide value and a formatter
			LocalDate date = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
			List<Task> tasks = ts.getTasksByDueDate(date);
			ctx.json(tasks);
		} else if (dueDate == null && isCompleted != null) {
			boolean completed = Boolean.parseBoolean(isCompleted);
			List<Task> tasks = ts.getTasksByCompletion(completed);
			ctx.json(tasks);
		} else {
			boolean completed = Boolean.parseBoolean(isCompleted);
			LocalDate date = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
			List<Task> tasks = ts.getTasksByCompletionAndDueDate(completed, date);
			ctx.json(tasks);		}
	}
	
	/*-
	 * Handles GET requests for the /tasks/{id} endpoint
	 * 	- returns a task by its id
	 */
	public static void getTaskById(Context ctx) {
		//Retrieves the value of the path param of name id
		String pathParamId = ctx.pathParam("id");
		//converts string to int for manipulation
		int taskId = Integer.parseInt(pathParamId);
		
		// calls on the getTaskById from the task dao, throws an exception if the task is not found
		Task t;
		try {
			t = ts.getTaskById(taskId);
			// serializes task object into json and adds it to the HTTP response
			ctx.json(t);
			ctx.status(200);
		} catch (TaskNotFoundException e) {
			ctx.status(404);
			ctx.result("Unable to find task of id " + taskId + ".");
			// TODO add proper logging
			e.printStackTrace();
		}
		

	}
	
	public static void addTask(Context ctx) {
		//Retrieves new Task from the HTTP body and converts it into a java object of type Task
		Task newTask = ctx.bodyAsClass(Task.class);
	
		/*-
		 * ts.createTask returns true if the task was successfully created
		 */
		if(ts.createTask(newTask)) {
			ctx.status(HttpStatus.CREATED_201);
		}else {
			ctx.status(400);
			ctx.result("Unable to create task.");
		}
	}
	
	public static void updateTask(Context ctx) {
		ctx.status(HttpStatus.NOT_IMPLEMENTED_501);
		ctx.result("Update task has not been implemented yet.");
	}
	
	public static void deleteTask(Context ctx) {
		ctx.status(HttpStatus.NOT_IMPLEMENTED_501);
		ctx.result("delete task has not been implemented yet.");
	}
}
