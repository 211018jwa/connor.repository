package com.revature.dto;

import java.util.Objects;

public class AddOrUpdateClientDTO {
	
	private String firstName;
	private String lastName;
	private int age;
	private String address;
	private String phoneNumber;
	
	public AddOrUpdateClientDTO() {
		
	}
	
	public AddOrUpdateClientDTO(String firstName, String lastName, int age, String address, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
		this.phoneNumber = phoneNumber;
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
		return Objects.hash(address, age, firstName, lastName, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrUpdateClientDTO other = (AddOrUpdateClientDTO) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phoneNumber, other.phoneNumber);
	}

	@Override
	public String toString() {
		return "AddOrUpdateClientDTO [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", address="
				+ address + ", phoneNumber=" + phoneNumber + "]";
	}
	
	

}
