package com.main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;


import com.model.User;
import com.model.Vehicle;

public class HibernateMainClass {

	public static void main(String[] args) {
		
		User user=new User();
		
		
		Vehicle v1=new Vehicle();
		Vehicle v2=new Vehicle();
		
		v1.setVehicleName("Honda");
		v1.setUser(user);
		
		v2.setVehicleName("Ford");
		v2.setUser(user);
		
		user.setUserName("Om");				
		
		SessionFactory factory=new  AnnotationConfiguration().configure("/com/hibernate.cfg.xml").buildSessionFactory();
		Session session= factory.openSession();
		session.beginTransaction();
		session.save(user);
		session.save(v1);
		session.save(v2);
		
		session.getTransaction().commit();
		session.close();
		
		
	}
}
