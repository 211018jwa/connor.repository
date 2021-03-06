package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.ClientModel;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.util.JDBCUtility;

public class ClientDao {
	
	public ClientModel addClient(AddOrUpdateClientDTO s2) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO Client (client_first_name, client_last_name, client_age, client_address, client_phone_number)"
						+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, s2.getFirstName());
			pstmt.setString(2, s2.getLastName());
			pstmt.setInt(3, s2.getAge());
			pstmt.setString(4, s2.getAddress());
			pstmt.setString(5, s2.getPhoneNumber());
			
			int records = pstmt.executeUpdate();
			
			if (records != 1) {
				throw new SQLException("Client unsuccessfully added");
			}
			
			ResultSet results = pstmt.getGeneratedKeys();
			
			results.next();
			int autoGenerate = results.getInt(1);
			
			return new ClientModel(autoGenerate, s2.getFirstName(), s2.getLastName(), s2.getAge(), s2.getAddress(), s2.getPhoneNumber());
		}
	}
	
	public List<ClientModel> getAllClients() throws SQLException {
		
		List<ClientModel> clientsList = new ArrayList();
		
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "SELECT * FROM Client";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
				int id = result.getInt("client_id");
				String firstName = result.getString("client_first_name");
				String lastName = result.getString("client_last_name");
				int age = result.getInt("client_age");
				String address = result.getString("client_address");
				String phoneNumber = result.getString("client_phone_number");
				
				ClientModel c = new ClientModel(id, firstName, lastName, age, address, phoneNumber);
				
				clientsList.add(c);
			}
		}
		
		return clientsList;
	}
	
	public ClientModel getClientById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM Client WHERE client_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				return new ClientModel(result.getInt("client_id"), result.getString("client_first_name"), result.getString("client_last_name"), 
						result.getInt("client_age"), result.getString("client_address"), result.getString("client_phone_number"));
			} else {
				return null;
			}
		}
	}
	
	public ClientModel updateClient(int clientId, AddOrUpdateClientDTO client) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE Client "
						+ "SET client_first_name = ?,"
						+ "client_last_name = ?, "
						+ "client_age = ?, "
						+ "client_address = ?, "
						+ "client_phone_number = ?"
						+ "WHERE " 
						+ "client_id = ?;";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setInt(3, client.getAge());
			pstmt.setString(4, client.getAddress());
			pstmt.setString(5, client.getPhoneNumber());
			pstmt.setInt(6, clientId);
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated != 1) {
				throw new SQLException("Client update has failed");
			}
		}
		
		return new ClientModel(clientId, client.getFirstName(), client.getLastName(), client.getAge(), client.getAddress(), client.getPhoneNumber());
	}
	
	public void deleteClientById(int id) throws SQLException {
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM Client \r\n"
					+ "WHERE client_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			int recordsDeleted = pstmt.executeUpdate();
			
			if (recordsDeleted != 1) {
				throw new SQLException("Deletion of Client unsuccessful");
			}
	}

	}
}
	
	
