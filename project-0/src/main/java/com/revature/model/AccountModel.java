package com.revature.model;

import java.util.Objects;

public class AccountModel {
	
	private int id;
	private String number;
	private String type;
	private String amount;
	private int clientId;
	
	public AccountModel() {
		
	}
	
	public AccountModel(int id, String number, String type, String amount, int clientId) {
		super();
		this.id = id;
		this.number = number;
		this.type = type;
		this.amount = amount;
		this.clientId = clientId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public int getClientId() {
		return clientId;
	}
	
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, clientId, id, number, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountModel other = (AccountModel) obj;
		return Objects.equals(amount, other.amount) && clientId == other.clientId && id == other.id
				&& Objects.equals(number, other.number) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "AccountModel [id=" + id + ", number=" + number + ", type=" + type + ", amount=" + amount + ", clientId="
				+ clientId + "]";
	}

	

}
