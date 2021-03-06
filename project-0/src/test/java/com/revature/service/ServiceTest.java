package com.revature.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.ClientNotFound;
import com.revature.exceptions.AccountNotFound;
import com.revature.exceptions.InvalidParameter;
import com.revature.Dao.ClientDao;
import com.revature.Dao.AccountDao;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.model.ClientModel;
import com.revature.model.AccountModel;

public class ServiceTest {
	
	private ServiceTest sut;
	
	@Test
	public void testGetAllClientsPositive() throws SQLException {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		ClientModel client1 = new ClientModel(10, "James", "Huffington", 24, "1212 Lake Blv", "444 909-8878");
		ClientModel client2 = new ClientModel(20, "Henry", "Wlliams", 26, "4534 Coral Ave.", "979 0655-0032");
		ClientModel client3 = new ClientModel(30, "Allison", "Jamie", 28, "999 West Branch Ave.", "888 454-6637");
		
		List<ClientModel> clientsFromDao = new ArrayList<>();
		clientsFromDao.add(client1);
		clientsFromDao.add(client2);
		clientsFromDao.add(client3);
		
		when(mockClientDao.getAllClients()).thenReturn(clientsFromDao);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		List<ClientModel> actual = clientService.getAllClients();
		
		List<ClientModel> expected = new ArrayList<>();
		
		expected.add(new ClientModel(10, "James", "Huffington", 24, "1212 Lake Blv", "444 909-8878"));
		expected.add(new ClientModel(20, "Henry", "Wlliams", 26, "4534 Coral Ave.", "979 0655-0032"));
		expected.add(new ClientModel(30, "Allison", "Jamie", 28, "999 West Branch Ave.", "888 454-6637"));
		
		Assertions.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testGetAllAccountsPositive() throws SQLException {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		AccountModel account1 = new AccountModel(10, "3366992255", "Checking", "12345.67", 10);
		AccountModel account2 = new AccountModel(11, "3366992256", "Savings", "24680.24", 10);
		AccountModel account3 = new AccountModel(20, "4878553222", "Checking", "15676.32", 20);
		
		List<AccountModel> accountsFromDao = new ArrayList<>();
		accountsFromDao.add(account1);
		accountsFromDao.add(account2);
		accountsFromDao.add(account3);
		
		when(mockAccountDao.getAllAccounts()).thenReturn(accountsFromDao);
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		List<AccountModel> actual = accountService.getAllAccounts();
		
		List<AccountModel> expected = new ArrayList<>();
		
		expected.add(new AccountModel(10, "3366992255", "Checking", "12345.67", 10));
		expected.add(new AccountModel(11, "3366992256", "Savings", "24680.24", 10));
		expected.add(new AccountModel(20, "4878553222", "Checking", "15676.32", 20));
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllClientsNegative() throws SQLException {
		ClientDao mockClientDao = mock(ClientDao.class);
		
		when(mockClientDao.getAllClients()).thenThrow(SQLException.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		Assertions.assertThrows(SQLException.class, () -> {
			clientService.getAllClients();
		});
	}
	
	@Test
	public void testGetAllAccountsNegative() throws SQLException {
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		when(mockAccountDao.getAllAccounts()).thenThrow(SQLException.class);
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		Assertions.assertThrows(SQLException.class, () -> {
			accountService.getAllAccounts();
		});
	}
	
	@Test
	public void testGetClientByIdPositive() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		when(mockClientDao.getClientById(eq(5))).thenReturn(new ClientModel(5, "John", "Jacobs", 26, "1581 Strange Blvd.", "999 845-3323"));
		
		ClientService clientService = new ClientService(mockClientDao);
		
		ClientModel actual = clientService.getClientById("5");
		
		Assertions.assertEquals(new ClientModel(5, "John", "Jacobs", 26, "1581 Strange Blvd.", "999 845-3323"), actual);
	}
	
	@Test
	public void testGetAccountByIdPositive() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		when(mockAccountDao.getAccountById(eq(5), eq(5))).thenReturn(new AccountModel(5, "3323453421", "Savings", "34323.78", 5));
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		AccountModel actual = accountService.getAccountById("5", "5");
		
		Assertions.assertEquals(new AccountModel(5, "3323453421", "Savings", "34323.78", 5), actual);
		
	}
	
	@Test
	public void testGetClientByIdNegative() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			clientService.getClientById("client");
		});
	}
	
	@Test
	public void testGetAccountByIdNegative() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			accountService.getAccountById("account", "client");
		});
	}
	
	@Test
	public void testAddClientPositive() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		AddOrUpdateClientDTO dtoDao = new AddOrUpdateClientDTO("Emily", "Winters", 27, "4454 Ocean Rogers Dr", "889 033-2212");
		
		when(mockClientDao.addClient(eq(dtoDao))).thenReturn(new ClientModel(9, "Emily", "Winters", 27, "4454 Ocean Rogers Dr", "889 033-2212"));
		
		ClientService clientService = new ClientService(mockClientDao);
		
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Emily", "Winters", 27, "4454 Ocean Rogers Dr", "889 033-2212");
		ClientModel actual = clientService.addClient(dto);
		
		ClientModel expected = (new ClientModel (9, "Emily", "Winters", 27, "4454 Ocean Rogers Dr", "889 033-2212"));
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testAddAccountPositive() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		AddOrUpdateAccountDTO dtoDao = new AddOrUpdateAccountDTO("2233221456", "Checking", "24543.89", 9);
		
		when(mockAccountDao.addAccount(eq(dtoDao))).thenReturn(new AccountModel(9, "2233221456", "Checking", "24543.89", 9));
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO("2233221456", "Checking", "24543.89", 9);
		AccountModel actual = accountService.addAccount(dto);
		
		AccountModel expected = (new AccountModel(9, "2233221456", "Checking", "24543.89", 9));
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testAddClientNegative() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			clientService.addClient(new AddOrUpdateClientDTO("", "", 0, "", ""));
		});
	}
	
	@Test
	public void testAddAccountNegative() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			accountService.addAccount(new AddOrUpdateAccountDTO("", "", "", 0));
		});
	}
	
	@Test
	public void testUpdateClientByIdPositive() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		when(mockClientDao.getClientById(eq(9))).thenReturn(new ClientModel(9, "Emily", "Winters", 27, "4454 Ocean Rogers Dr", "889 033-2212"));
		
		AddOrUpdateClientDTO updateClient = new AddOrUpdateClientDTO("Emily", "Winters", 28, "567 River Martin Dr", "889 033-2212");
		
		when(mockClientDao.updateClient(eq(9), eq(updateClient))).thenReturn(new ClientModel(9, "Emily", "Winters", 28, "567 River Martin Dr", "889 033-2212"));
		
		ClientService clientService = new ClientService(mockClientDao);
		
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Emily", "Winters", 28, "567 River Martin Dr", "889 033-2212");
		ClientModel actual = clientService.editClientById("9", dto);
		
		ClientModel expected = (new ClientModel (9, "Emily", "Winters", 28, "567 River Martin Dr", "889 033-2212"));
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateAccountByIdPositive() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		when(mockAccountDao.getAccountById(eq(9), eq(9))).thenReturn(new AccountModel(9, "2233221456", "Checking", "24543.89", 9));
		
		AddOrUpdateAccountDTO updateAccount = new AddOrUpdateAccountDTO("2233221456", "Savings", "30067.89", 9);
		
		when(mockAccountDao.updateAccount(eq(9), eq(9), eq(updateAccount))).thenReturn(new AccountModel(9, "2233221456", "Savings", "30067.89", 9));
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO("2233221456", "Savings", "30067.89", 9);
		AccountModel actual = accountService.editAccount("9", "9", dto);
		
		AccountModel expected = (new AccountModel(9, "2233221456", "Savings", "30067.89", 9));
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateClientByIdNegative() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			clientService.editClientById("rt", null);
		});
	}
	
	@Test
	public void testUpdateAccountByIdNegative() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		AddOrUpdateAccountDTO updateDto = new AddOrUpdateAccountDTO("33443255654", "Savings", "4005.67", 23);
		Assertions.assertThrows(InvalidParameter.class, () -> {
			accountService.editAccount("1", "rt", updateDto);
		});
	}
	
	@Test
	public void testDeleteClientByIdPositive() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		when(mockClientDao.getClientById(eq(9))).thenReturn(new ClientModel(9, "Emily", "Winters", 28, "567 River Martin Dr", "889 033-2212"));
		
		ClientService clientService = new ClientService(mockClientDao);
		
		ClientModel actual = clientService.deleteClientById("9");
		
		ClientModel expected = (new ClientModel (9, "Emily", "Winters", 28, "567 River Martin Dr", "889 033-2212"));
		Assertions.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testDeleteAccountByIdPositive() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		when(mockAccountDao.getAccountById(eq(9), eq(9))).thenReturn(new AccountModel(9, "2233221456", "Checking", "24543.89", 9));
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		AccountModel actual = accountService.deleteAccountById("9", "9");
		
		AccountModel expected = (new AccountModel(9, "2233221456", "Checking", "24543.89", 9));
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testDeleteClientByIdNegative() throws SQLException, InvalidParameter, ClientNotFound {
		
		ClientDao mockClientDao = mock(ClientDao.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			clientService.deleteClientById(null);
		});
	}
	
	@Test
	public void testDeleteAccountByIdNegative() throws SQLException, InvalidParameter, AccountNotFound {
		
		AccountDao mockAccountDao = mock(AccountDao.class);
		
		AccountService accountService = new AccountService(mockAccountDao);
		
		Assertions.assertThrows(InvalidParameter.class, () -> {
			accountService.deleteAccountById(null, null);
		});
	}

}
