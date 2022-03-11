package com.revature;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.models.Task;

import io.javalin.Javalin;

public class Driver {

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
		
		Task t1 = new Task(0, "My First Task!", false);
		
		/*-
		 * GET /tasks
		 * 		- returns the task
		 */
		
		app.get("tasks", (ctx) -> {
			ctx.json(t1);
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
			System.out.println(t);
			ctx.status(HttpStatus.CREATED_201);
		});
		
	}
}
