package com.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.spring.model.Persona;

@Repository
public class PersonaDaoImpl implements IpersonaDao {

	@Autowired
	private SessionFactory  sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Persona> listPersons() {
		
		return sessionFactory.getCurrentSession().createNativeQuery("SELECT * FROM persona").getResultList();
	}

	@Override
	public boolean saveOrUpdate(Persona persona) {
		
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(persona);
			
		} catch (Exception e) {
			
			return false;
		}
		return true;
	}

	@Override
	public boolean removePerson(Persona persona) {

		try {
			sessionFactory.getCurrentSession().remove(persona);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Persona findPersonById(int id) {
		return sessionFactory.getCurrentSession().find(Persona.class, id);
	}

}
