package com;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.model.User;
import com.model.Vehicle;

public class MainClass {

	public static void main(String[] args) {
		
		User user=new User();
		
		Vehicle v1=new Vehicle();
		Vehicle v2=new Vehicle();
		
		v1.setVehicleName("BMW");
		v2.setVehicleName("AUDI");		
		
		user.setUserName("Rohan");
		
		user.getVehicle().add(v1);
		user.getVehicle().add(v2);		
		
		SessionFactory factory = null;
		try{
		factory=new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();				
		
		session.save(v1);
		session.save(v2);
		
		session.save(user);
		
		tx.commit();
		session.close();
		factory.close();
	}
	

}
