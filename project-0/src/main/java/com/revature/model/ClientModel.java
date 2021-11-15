package com.revature.model;

import java.util.Objects;

public class ClientModel {
	
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private String address;
	private String phoneNumber;
	
	public ClientModel() {
		
	}
	
	public ClientModel(int id, String firstName, String lastName, int age, String address, String phoneNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, age, firstName, id, lastName, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientModel other = (ClientModel) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(firstName, other.firstName)
				&& id == other.id && Objects.equals(lastName, other.lastName)
				&& Objects.equals(phoneNumber, other.phoneNumber);
	}

	@Override
	public String toString() {
		return "ClientModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", address=" + address + ", phoneNumber=" + phoneNumber + "]";
	}
	
	

}
