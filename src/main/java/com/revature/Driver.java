package com.revature;

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
	}
}
