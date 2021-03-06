package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.Dao.ClientDao;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.ClientNotFound;
import com.revature.exceptions.InvalidParameter;
import com.revature.model.ClientModel;

public class ClientService {
	
	private Logger logger = LoggerFactory.getLogger(ClientService.class);
	
	private ClientDao clientDao;
	
	public ClientService() {
		this.clientDao = new ClientDao();
	}
	
	public ClientService(ClientDao mockClientDao) {
		this.clientDao = mockClientDao;
	}

	public ClientModel addClient (AddOrUpdateClientDTO dto) throws SQLException, InvalidParameter, ClientNotFound {
		if (dto.getFirstName().trim().equals("") || dto.getLastName().trim().equals("")) {
			throw new InvalidParameter("First name and/or last name is blank. Please fill them both out");
		}
		
		if (dto.getAge() < 0) {
			throw new InvalidParameter("Age can't be less than 0. Enter in a valid age.");
		}
		
		if (dto.getAddress().trim().equals("")) {
			throw new InvalidParameter("Address can't be left blank. Please fill out your address.");
		}
		
		if (dto.getPhoneNumber().trim().equals("")) {
			throw new InvalidParameter("Phone number cannot have letters or left blank. Please fill out a valid phone number");
		}
		
		dto.setFirstName(dto.getFirstName().trim());
		dto.setLastName(dto.getLastName().trim());
		dto.setAddress(dto.getAddress().trim());
		dto.setPhoneNumber(dto.getPhoneNumber().trim());
		
		ClientModel insertClient = this.clientDao.addClient(dto);
		
		return insertClient;
	}
	
	public List<ClientModel> getAllClients() throws SQLException {
		List<ClientModel> clients = this.clientDao.getAllClients();
		
		return clients;
	}
	
	public ClientModel getClientById(String clientId) throws SQLException, InvalidParameter, ClientNotFound {
		
		logger.info("Invoked getClientById method");
		
		try {
			int id = Integer.parseInt(clientId);
			
			ClientModel c = this.clientDao.getClientById(id);
			
			if (c == null) {
				logger.warn("Client id does not exist");
				throw new ClientNotFound("Invalid id for client");
			}
			return c;
		}catch(NumberFormatException e) {
			throw new InvalidParameter("Id provided is not an int");
		}
	}
	
	public ClientModel deleteClientById(String clientId) throws InvalidParameter, SQLException, ClientNotFound {
		
		logger.info("Invoked deleteClientById method");
		
		try {
			int id = Integer.parseInt(clientId); 
			
			ClientModel client = this.clientDao.getClientById(id);
			if (client == null) {
				logger.warn("Client id does not exist");
				throw new ClientNotFound("Invalid id, cannot delete client");
			}
			return client;
		} catch(NumberFormatException e) {
			throw new InvalidParameter("Invalid id has been provided");
		}
	}

	public ClientModel editClientById(String id, AddOrUpdateClientDTO dto) throws SQLException, InvalidParameter, ClientNotFound {
		
		logger.info("Invoked editClientById method");
		
		try {
			
		int clientId = Integer.parseInt(id);
		
		ClientModel clientIdUpdate = this.clientDao.getClientById(clientId);
		if (clientIdUpdate == null) {
			logger.warn("Client id does not exist");
			throw new ClientNotFound("Client id that was just entered has not been found.");
		}
		
		ClientModel clientEditing = this.clientDao.updateClient(clientId, dto);
		return clientEditing;
		} catch (NumberFormatException e) {
			throw new InvalidParameter("Id is invalid and will not be accepted. Please try again");
		}
	}

}
