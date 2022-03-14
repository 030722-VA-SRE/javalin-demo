package com.revature;

import java.time.LocalDate;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.models.Task;
import com.revature.persitence.TaskDao;
import com.revature.persitence.TaskPostgres;

import io.javalin.Javalin;

public class Driver {

	public static void main(String[] args) {
		basicTaskApp();

	}
	
	public static void basicJavalinSetUp() {
		Javalin app = Javalin.create();
		app.start(8080);
		
		/*-
		 * Basic endpoint set up returning plain txt
		 */
		app.get("hello", (ctx) -> {
			ctx.result("Hello from Javalin!");
		});
		
		/*-
		 * Basic endpoint returning a JSON object
		 */
		app.get("json", (ctx) ->{
			Task t = new Task();
			t.setName("Testing Javalin!");
			t.setDueDate(LocalDate.now());
			ctx.json(t);
		});
		
		/*-
		 * Basic endpoint deserializing JSON back to a Java task object
		 */
		app.post("json", (ctx) ->{
			Task t = ctx.bodyAsClass(Task.class);
			System.out.println(t);
		});
	}

	public static void basicTaskApp() {
		TaskDao taskDao = new TaskPostgres();
		
		/*-
		 * All in one application setup, here we are using an ArrayList to mimic the idea of persistence.
		 * 	- Tasks added via the API will be added to the ArrayList
		 * 	- Tasks can then be retrieved by iterating through the ArrayList to retrieve/modify the desired tasks 
		 */
		
		Javalin app = Javalin.create().start(8080);
		
		app.get("tasks", (ctx) -> {
			ctx.json(taskDao.getTasks());
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
			
			Task t = taskDao.getTaskById(taskId);
			
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
			int generatedId = taskDao.addTask(newTask);
			/*-
			 * if the task already exists, returns status code 400 with a message about why
			 * else if the task doesn't exist, add the task to the arraylist and set the status code to 201 
			 */
			if(generatedId == -1) {
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
			
			boolean deletedTask = taskDao.deleteTask(taskId);
			if(deletedTask) {
				ctx.status(200);
			} else {
				ctx.status(404);
			}
		});
	}
}
