package com.vnpt.ssdc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {
	
	private Long id;
	
	private String email;
	
	private String phone;
	
	private String password;
	
	private String machineId;
	
	private String newPassword;

	public Users() {
	}
	
	public Users(Long id, String email, String phone, String password, String machineId, String newPassword) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.machineId = machineId;
		this.newPassword = newPassword;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
