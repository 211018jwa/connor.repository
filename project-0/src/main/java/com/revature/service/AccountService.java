package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.Dao.AccountDao;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.exceptions.AccountNotFound;
import com.revature.exceptions.InvalidParameter;
import com.revature.model.AccountModel;

public class AccountService {
	
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	private AccountDao accountDao;
	
	public AccountService() {
		this.accountDao = new AccountDao();
	}
	
	public AccountService(AccountDao mockAccountDao) {
		this.accountDao = mockAccountDao;
	}

	public AccountModel addAccount (AddOrUpdateAccountDTO dto) throws SQLException, InvalidParameter, AccountNotFound {
		if (dto.getNumber().trim().equals("")) {
			throw new InvalidParameter("Account number cannot be blank. Please fill out your account number");
		}
		
		Set<String> validType = new HashSet<>();
		validType.add("Checking");
		validType.add("Savings");
		
		if (!validType.contains(dto.getType())) {
			throw new InvalidParameter("Your account can only accept either the words Checking or Savings");
		}
		
		if (Double.parseDouble(dto.getAmount()) < 0) {
			throw new InvalidParameter("The amount of money you have cannot be less than 0");
		}
		
		if (dto.getClientId() < 0) {
			throw new InvalidParameter("The client id cannot be 0. Please enter in the Client's id");
		}
		
		dto.setNumber(dto.getNumber().trim());
		
		AccountModel insertAccount = this.accountDao.addAccount(dto);
		
		return insertAccount;
	}
	
	public List<AccountModel> getAllAccounts() throws SQLException {
		List<AccountModel> accounts = this.accountDao.getAllAccounts();
		
		return accounts;
	}
	
	public AccountModel getAccountById(String accountId, String clientId) throws SQLException, InvalidParameter, AccountNotFound {
		
		try {
			int id1 = Integer.parseInt(accountId);
			int id2 = Integer.parseInt(clientId);
			
			AccountModel a = this.accountDao.getAccountById(id1, id2);
			
			if (a == null) {
				throw new AccountNotFound("Invalid id for account");
			}
			return a;
		}catch(NumberFormatException e) {
			throw new InvalidParameter("Id provided is not an int");
		}
	}
	
	public AccountModel deleteAccountById(String accountId, String clientId) throws InvalidParameter, SQLException, AccountNotFound {
		try {
			int id1 = Integer.parseInt(accountId); 
			int id2 = Integer.parseInt(clientId);
			
			AccountModel account = this.accountDao.getAccountById(id1, id2);
			if (account == null) {
				throw new AccountNotFound("Invalid id, cannot delete account");
			}
			
			this.accountDao.deleteAccountById(id1, id2);
			return account;
		} catch(NumberFormatException e) {
			throw new InvalidParameter("Invalid id has been provided");
		}
	}

	public AccountModel editAccount(String id1, String id2, AddOrUpdateAccountDTO dto) throws SQLException, InvalidParameter, AccountNotFound {
		
		logger.info("Invoke editAccountMethod method");
		
		try {
			int accountId = Integer.parseInt(id1);
			int clientId = Integer.parseInt(id2);
		
			AccountModel accountEditing = this.accountDao.updateAccount(accountId, clientId, dto);
		return accountEditing;
		} catch(NumberFormatException e) {
			logger.warn("Account id or client id does not exist");
			throw new InvalidParameter("One or more invalid id(s) has been provided");
		}
	}

}
