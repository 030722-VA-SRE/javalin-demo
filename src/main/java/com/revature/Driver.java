package com.revature;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.models.Task;

import io.javalin.Javalin;

public class Driver {

	private static List<Task> tasks = new ArrayList<>();;
	
	public static void main(String[] args) {
		Javalin app = Javalin.create( (config) -> {
			// pass any configuration associated with Javalin
		});
		
		// pass in a value to give a port number, default it's port 8080
		app.start();
		
		/*-
		 * HTTP Request
		 * 		- Verb: GET
		 * 		- URL: hello
		 * 		- Header:
		 * 		- Body:
		 * HTTP Response
		 * 		- Body: "Hello World!"
		 */
		app.get("hello", (ctx) ->{
			ctx.status(256);
			ctx.result("Hello World");
		});
		
		
		/*-
		 * 	As a user, I can view all tasks.
				GET /tasks
			As a user, I can add a new task.
				POST /tasks
			As a user, I can update a task.
				PUT /tasks/{id}
			As a user, I can view task by ID.
				GET /tasks/{id}
			As a user, I can delete a task by ID.
				DELETE /tasks/{id}
		 */
		
		Task t1 = new Task("My First Task!", false);
		Task t2 = new Task("My Second Task!", true);
		Task t3 = new Task("My Third Task!", false);
		
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
		
		/*-
		 * GET /tasks
		 * 		- returns the all tasks
		 */
		
		app.get("tasks", (ctx) -> {
			ctx.json(tasks);
		});
		
		app.get("tasks/{id}", (ctx) -> {
			// extracts the id from the path param in the url, and converts it to an int
			int id = Integer.parseInt(ctx.pathParam("id"));
			
			Task task = null;
			
			// iterate over tasks arraylist, to retrieve the task of that id
			for(int i = 0; i < tasks.size(); i++) {
				// tasks = ArrayList<Task>
				if(tasks.get(i).getId() == id) {
					task = tasks.get(i);
				}
			}
			
			// if the loop finds a task of that id, returns that task else, sends 404
			if(task == null) {
				ctx.status(404);
			} else {
				ctx.json(task);
			}
			
		});
		/*-
		 * POST /tasks
		 * 		- HTTP request
		 * 			- Body
		 * 				- Task Object (JSON
		 * 		- add an object
		 */
		app.post("tasks", (ctx) ->{
			Task t =ctx.bodyAsClass(Task.class);
			// behavior to persist the object
			tasks.add(t);
			ctx.status(HttpStatus.CREATED_201);
		});
		
		/*
		 * Update
		 * Remove
		 */
	}
}
