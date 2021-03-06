package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.AccountModel;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.util.JDBCUtility;

public class AccountDao {
	
	public AccountModel addAccount(AddOrUpdateAccountDTO s2) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO Account (account_number, account_type, account_amount, client_id)"
						+ "VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, s2.getNumber());
			pstmt.setString(2, s2.getType());
			pstmt.setDouble(3, Double.parseDouble(s2.getAmount()));
			pstmt.setInt(4, s2.getClientId());

			
			int records = pstmt.executeUpdate();
			
			if (records != 1) {
				throw new SQLException("Client unsuccessfully added");
			}
			
			ResultSet results = pstmt.getGeneratedKeys();
			
			results.next();
			int autoGenerate = results.getInt(1);
			
			return new AccountModel(autoGenerate, s2.getNumber(), s2.getType(), s2.getAmount(), s2.getClientId());
		}
	}
	
	public List<AccountModel> getAllAccounts() throws SQLException {
		
		List<AccountModel> accountsList = new ArrayList();
		
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "SELECT * FROM Account";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
				int id = result.getInt("account_id");
				String number = result.getString("account_number");
				String type = result.getString("account_type");
				String amount = result.getString("account_amount");
				int clientId = result.getInt("client_id");
				
				AccountModel a = new AccountModel(id, number, type, amount, clientId);
				
				accountsList.add(a);
			}
		}
		
		return accountsList;
	}
	
	public AccountModel getAccountById(int clientId, int accountId) throws SQLException {
		try(Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT *\r\n"
					+ "FROM Account\r\n"
					+ "WHERE account_id = ? AND client_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, accountId);
			pstmt.setInt(2, clientId);
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				return new AccountModel(result.getInt("account_id"), result.getString("account_number"), result.getString("account_type"), result.getString("account_amount"), result.getInt("client_id"));
			} else {
				return null;
			}
		}
	}
	
	public AccountModel updateAccount(int id1, int id2, AddOrUpdateAccountDTO account) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE Account SET account_number = ?, account_type = ?, account_amount = ? WHERE account_id = ? AND client_id = ?;\r\n"
					+ "";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, account.getNumber());
			pstmt.setString(2, account.getType());
			pstmt.setDouble(3 ,Double.parseDouble(account.getAmount()));
			pstmt.setInt(4, id1);
			pstmt.setInt(5, id2);
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated != 1) {
				throw new SQLException("Update has been unsuccessful");
			}
		}
		
		return new AccountModel(id1, account.getNumber(), account.getType(), account.getAmount(), id2);
	}
	
	public void deleteAccountById(int clientId, int accountId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "DELETE FROM Account WHERE account_id = ? AND client_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, accountId);
			pstmt.setInt(2, clientId);
			
			int recordsDeleted = pstmt.executeUpdate();
			
			if (recordsDeleted != 1) {
				throw new SQLException("Deletion has been unsuccessful");
			}
		}
	}

}
