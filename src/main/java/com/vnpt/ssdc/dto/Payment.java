package com.vnpt.ssdc.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Payment {	
	private String email;
	private String epayDate;	
	private String name;	
	private String price;
	private String epayDateStart;
	private String epayDateEnd;
	private String packageId;
	private Long id;
	
	public Payment() {
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

	public String getEpayDate() {
		return epayDate;
	}

	public void setEpayDate(String epayDate) {
		this.epayDate = epayDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEpayDateStart() {
		return epayDateStart;
	}

	public void setEpayDateStart(String epayDateStart) {
		this.epayDateStart = epayDateStart;
	}

	public String getEpayDateEnd() {
		return epayDateEnd;
	}

	public void setEpayDateEnd(String epayDateEnd) {
		this.epayDateEnd = epayDateEnd;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	
}
