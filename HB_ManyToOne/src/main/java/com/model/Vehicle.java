package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="VEHICLE")
public class Vehicle {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="VEHICLE_ID")
	private int vehicleID;
	
	@Column(name="VEHICLE_NAME")	
	private String vehicleName;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user=user;
	}
	
	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	
	
	
	
}
