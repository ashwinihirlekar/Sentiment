package com.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="USER")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private int userID;
	
	@Column(name="USER_NAME")
	private String userName;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="USER_VEHICLE",
				joinColumns=@JoinColumn(name="USER_ID",nullable=false),
				inverseJoinColumns=@JoinColumn(name="VEHICLE_ID",nullable=false))
	
	private Collection<Vehicle> vehicle=new ArrayList<Vehicle>();
	
	public Collection<Vehicle> getVehicle(){
		return vehicle;
		
	}
	public void setVehicle(Collection<Vehicle> vehicle){
		this.vehicle=vehicle;
	}
}
