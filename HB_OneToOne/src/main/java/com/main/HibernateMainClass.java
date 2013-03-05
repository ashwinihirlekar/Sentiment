package com.main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

import com.model.User;
import com.model.Vehicle;

public class HibernateMainClass {

	public static void main(String[] args) {

		User user = new User();

		Vehicle v1 = new Vehicle();

		v1.setVehicleName("Ferrari");

		user.setUserName("Om");
		user.setVehicle(v1);

		SessionFactory factory = new AnnotationConfiguration().configure(
				"/com/hibernate.cfg.xml").buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		session.save(v1);
		session.save(user);

		session.getTransaction().commit();
		session.close();

	}
}
