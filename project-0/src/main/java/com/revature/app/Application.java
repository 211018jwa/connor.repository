package com.revature.app;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AccountClientController;


import io.javalin.Javalin;

public class Application {

	public static void main(String[] args) {
		
		Javalin app = Javalin.create();
		
		Logger logger = LoggerFactory.getLogger(Application.class);
		
		app.before(ctx -> {
			logger.info(ctx.method() + " request has gone to the " + ctx.path() + " endpoint");
		});
		
		AccountClientController controller = new AccountClientController();
		
		controller.registerEndpoints(app);
		
		app.start();

	}

}
