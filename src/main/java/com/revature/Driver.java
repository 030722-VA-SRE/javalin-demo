package com.revature;

import java.time.LocalDate;
import java.util.ArrayList;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.models.Task;

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
		ArrayList<Task> tasks = new ArrayList<>();
		
		/*-
		 * All in one application setup, here we are using an ArrayList to mimic the idea of persistence.
		 * 	- Tasks added via the API will be added to the ArrayList
		 * 	- Tasks can then be retrieved by iterating through the ArrayList to retrieve/modify the desired tasks 
		 */
		// creating 5 tasks
		for(int i = 0; i < 5; i++){
			Task t = new Task();
			t.setId(Task.taskCounter);
			t.setName("Task number: " + Task.taskCounter);
			t.setDueDate(LocalDate.now().plusDays(Task.taskCounter));
			tasks.add(t);
		}
		
		Javalin app = Javalin.create().start(8080);
		
		app.get("tasks", (ctx) -> {
			ctx.json(tasks);
			ctx.queryParam("completed");
			ctx.queryParam("dueDate");
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
			
			/*-
			 * once the id has been retrieved, we can iterate through the arraylist to retrieve the relevant item
			 * 		- note: the id of the task and the index might not be the same if elements are deleted!
			 */
			Task t = null;
			for(Task task :tasks) {
				if(task.getId() == taskId) {
					t = task;
					break;
				}
			}
			
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
			boolean alreadyExists = false;
			// iterating tasks of task ArrayList and comparing tasks' names with the new task, if they match set already exists to true
			for(Task task : tasks) {
				if(newTask.getName().equals(task.getName())) {
					alreadyExists = true;
					break;
				}
			}
			/*-
			 * if the task already exists, returns status code 400 with a message about why
			 * else if the task doesn't exist, add the task to the arraylist and set the status code to 201 
			 */
			if(alreadyExists) {
				ctx.status(400);
				ctx.result("Task of name '" + newTask.getName() + "' already exists.");
			}else {
				tasks.add(newTask);
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
			ctx.status(404);
			
			for(int i = 0; i < tasks.size(); i++) {
				if(tasks.get(i).getId() == taskId) {
					// if a task of that id is found, remove task from the arrayList and set status to success
					tasks.remove(i);
					ctx.status(200);
				}
			}
		});
	}
}
