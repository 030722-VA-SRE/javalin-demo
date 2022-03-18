package com.revature;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import com.revature.controllers.TaskController;

import io.javalin.Javalin;

public class Driver {

	public static void main(String[] args) {

		Javalin app = Javalin.create((config) -> {
			config.enableCorsForAllOrigins();
			/*-
			 * CORS refers to Cross Origin Resource Sharing
			 * 	- protective mechanism built into most browsers
			 * 	- restricts resources to be only allowed by webpages on the same domain
			 * 
			 * This is not relevant to p0, but it is helpful for front ends using this api
			 */
			config.defaultContentType = "application/json";
			// setting a default content type for HTTP Responses
		});

		app.start(8080);

		/*-
		 * Instead of specifying all of the behaviors for the endpoints in HTTP handlers/lambdas we can assign each endpoints and HTTP methods to different methods in a controller class
		 * 	- do not worry about the specifics of the syntax
		 * 	- do note that it requires static imports that might not auto import (top of the file)
		 * 
		 * The following code is used to route the HTTP requests to the appropriate methods in the controller package to handle them 
		 */
		app.routes(() -> {
			// handles url starting with /tasks
			path("tasks", () -> {
				// assigns getTasks method from TaskController to /tasks - GET
				get(TaskController::getTasks);
				// assigns addTask method from TaskController to /tasks - POST
				post(TaskController::addTask);
				
				// handles url starting with /tasks/{id}
				path("{id}", () -> {
					// assigns getTaskById method from TaskController to /tasks/{id} - GET
					get(TaskController::getTaskById);
					// assigns updateTask method from TaskController to /tasks/{id} - UPDATE
					put(TaskController::updateTask);
					// assigns deleteTask method from TaskController to /tasks/{id} - DELETE
					delete(TaskController::deleteTask);
				});
			});
		});
	}

}
