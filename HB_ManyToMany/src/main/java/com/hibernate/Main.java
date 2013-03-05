package com.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.model.User;
import com.model.Vehicle;

public class Main {

	public static void main(String[] args) {
		SessionFactory sf = new AnnotationConfiguration().configure(
				"hibernate.cfg.xml").buildSessionFactory();
		Session session = sf.openSession();		

		Vehicle v1 = new Vehicle();
		Vehicle v2 = new Vehicle();

		v1.setVehicleName("Bugatti");
		v2.setVehicleName("Nissan");
		
		User u1 = new User();
		User u2 = new User();

		u1.setUserName("Aarchi");
		u1.getVehicle().add(v1);
		u1.getVehicle().add(v2);

		u2.setUserName("Jit");
		u2.getVehicle().add(v1);
		u2.getVehicle().add(v2);

		
		session.beginTransaction();
		session.save(u1);
		session.save(u2);
		session.save(v1);
		session.save(v2);
		session.getTransaction().commit();
	}

}
