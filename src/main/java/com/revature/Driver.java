package com.revature;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.models.Task;
import com.revature.persistence.TaskDao;
import com.revature.services.TaskService;

import io.javalin.Javalin;

public class Driver {

	public static void main(String[] args) {
		TaskService ts = new TaskService();
	
		
		Javalin app = Javalin.create().start(8080);
		
		app.get("tasks", (ctx) -> {
			ctx.json(ts.getAllTasks());
			// adding a comment for push
		});
		
		
		app.get("tasks/{id}", (ctx) -> {
			/*-
			 *  here the id value requested is passed via a path param where {id} represents the value, ie:
			 *  	- localhost:8080/tasks/2
			 *  	- localhost:8080/tasks/5
			 *  we are retrieving this value as a string using the ctx object which we are then converting to an int for manipulation
			 */
			String pathParamId = ctx.pathParam("id");
			int taskId = Integer.parseInt(pathParamId);
			
			Task t = ts.getTaskById(taskId);
			
			/*-
			 * now that the arraylist has been searched, we can provide the relevant HttpResponse based on whether the task was found or not
			 */
			
			if(t == null) {
				ctx.status(404);
			} else {
				ctx.json(t);
			}
		});
		
		app.post("tasks", (ctx) ->{
			Task newTask = ctx.bodyAsClass(Task.class);
			
			/*-
			 * example of some validation:
			 * 	- if the task name already exists, does not add a new task to the ArrayList
			 */
			/*-
			 * if the task already exists, returns status code 400 with a message about why
			 * else if the task doesn't exist, add the task to the arraylist and set the status code to 201 
			 */
			if(ts.createTask(newTask)) {
				ctx.status(400);
				ctx.result("Task of name '" + newTask.getName() + "' already exists.");
			}else {
				ctx.status(HttpStatus.CREATED_201);
			}
		});
		
		app.delete("tasks/{id}", (ctx) ->{
			// retrieving id from the path param and converting to an int
			String pathParamId = ctx.pathParam("id");
			int taskId = Integer.parseInt(pathParamId);
			
			/*-
			 *  set a default response to be overriden if a task is deleted
			 *  	- ie: if no tasks of that id is found in the arrayList no task is deleted
			 */
			
			boolean deletedTask = ts.deleteTaskById(taskId);
			if(deletedTask) {
				ctx.status(200);
			} else {
				ctx.status(404);
			}
		});
	}
	
}
