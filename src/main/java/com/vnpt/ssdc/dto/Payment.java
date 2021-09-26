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
	private String phone;
	private String machineId;
	private String status;
	private String statusName;
	private String nextPayDate;
	private String code;
	private String payDate;
	private String nextPayDateStart;
	private String nextPayDateEnd;
	private Long mapId;
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNextPayDate() {
		return nextPayDate;
	}

	public void setNextPayDate(String nextPayDate) {
		this.nextPayDate = nextPayDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getNextPayDateStart() {
		return nextPayDateStart;
	}

	public void setNextPayDateStart(String nextPayDateStart) {
		this.nextPayDateStart = nextPayDateStart;
	}

	public String getNextPayDateEnd() {
		return nextPayDateEnd;
	}

	public void setNextPayDateEnd(String nextPayDateEnd) {
		this.nextPayDateEnd = nextPayDateEnd;
	}

	public Long getMapId() {
		return mapId;
	}

	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	
}
