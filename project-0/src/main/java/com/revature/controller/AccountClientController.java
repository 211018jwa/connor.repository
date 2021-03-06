package com.revature.controller;

import com.revature.model.ClientModel;
import com.revature.model.AccountModel;

import java.util.List;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.exceptions.InvalidParameter;
import com.revature.exceptions.ClientNotFound;
import com.revature.exceptions.AccountNotFound;
import com.revature.service.ClientService;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountClientController {
	
	private ClientService clientService;
	private AccountService accountService;
	
	public AccountClientController() {
		this.clientService = new ClientService();
		this.accountService = new AccountService();
	}
	
	private Handler editClientById = (ctx) -> {
		String clientId = ctx.pathParam("client_id");
		
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);
		ClientModel clientEdited = this.clientService.editClientById(clientId, dto);
		
		ctx.json(clientEdited);
	};
	
	private Handler editAccountById = (ctx) -> {
		String accountId = ctx.pathParam("account_id");
		String clientId = ctx.pathParam("client_id");
		
		AddOrUpdateAccountDTO dto = ctx.bodyAsClass(AddOrUpdateAccountDTO.class);
		AccountModel accountEdited = this.accountService.editAccount(accountId, clientId, dto);
		
		ctx.json(accountEdited);
	};
	
	private Handler addClient = (ctx) -> {
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);
		
		ClientModel c = this.clientService.addClient(dto);
		
		ctx.json(c);
		ctx.status(201);
	};
	
	private Handler getAllClients = (ctx) -> {
		List<ClientModel> clients = this.clientService.getAllClients();
		
		ctx.json(clients);
	};
	
	private Handler getClientById = (ctx) -> {
		String id = ctx.pathParam("client_id");
		
		try {
			ClientModel c = this.clientService.getClientById(id);
			
			ctx.json(c);
		} catch(InvalidParameter e) {
			ctx.status(400);
			ctx.json(e);
		} catch(ClientNotFound e) {
			ctx.status(400);
			ctx.json(e);
		}
	};
	
	private Handler deleteClientById = (ctx) -> {
		String id = ctx.pathParam("client_id");
		
		try {
			this.clientService.deleteClientById(id);
		}catch(InvalidParameter e) {
			ctx.status(400);
			ctx.json(e);
		}catch(ClientNotFound e) {
			ctx.status(400);
			ctx.json(e);
		}
	};
	
	private Handler addAccount = (ctx) -> {
		AddOrUpdateAccountDTO dto = ctx.bodyAsClass(AddOrUpdateAccountDTO.class);
		
		AccountModel a = this.accountService.addAccount(dto);
		
		ctx.json(a);
		ctx.status(201);
	};
	
	private Handler getAllAccounts = (ctx) -> {
		List<AccountModel> accounts = this.accountService.getAllAccounts();
		
		ctx.json(accounts);
	};
	
	private Handler getAccountById = (ctx) -> {
		String accountId = ctx.pathParam("account_id");
		String clientId = ctx.pathParam("client_id");
		try {
			AccountModel a = this.accountService.getAccountById(accountId, clientId);
			
			ctx.json(a);
		} catch(InvalidParameter e) {
			ctx.status(400);
			ctx.json(e);
		} catch(AccountNotFound e) {
			ctx.status(400);
			ctx.json(e);
		}
	};
	
	private Handler deleteAccountById = (ctx) -> {
		String accountId = ctx.pathParam("account_id");
		String clientId = ctx.pathParam("client_id");
		
		try {
			this.accountService.deleteAccountById(accountId, clientId);
		}catch(InvalidParameter e) {
			ctx.status(400);
			ctx.json(e);
		}catch(AccountNotFound e) {
			ctx.status(400);
			ctx.json(e);
		}
	};
	
	public void registerEndpoints(Javalin app) {
		
		app.post("/client", addClient);
		app.get("/client", getAllClients);
		app.get("/client/{client_id}", getClientById);
		app.put("client/{client_id}", editClientById);
		app.delete("/client/{client_id}", deleteClientById);
		app.post("/client/{client_id}/account", addAccount);
		app.get("/client/{client_id}/account", getAllAccounts);
		app.get("/client/{client_id}/account/{account_id}", getAccountById);
		app.put("/client/{client_id}/account/{account_id}", editAccountById);
		app.delete("/client/{client_id}/account/{account_id}", deleteAccountById);
	}

}
