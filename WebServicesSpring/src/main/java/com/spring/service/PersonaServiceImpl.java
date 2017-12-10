package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.IpersonaDao;
import com.spring.model.Persona;

@Service
@Transactional
public class PersonaServiceImpl implements IservicePersona {

	@Autowired
	private IpersonaDao personaDao;
	
	@Override
	public List<Persona> listPersons() {
		
		return personaDao.listPersons();
	}

	@Override
	public boolean saveOrUpdate(Persona persona) {
		
		return personaDao.saveOrUpdate(persona);
	}

	@Override
	public boolean removePerson(Persona persona) {
		
		return personaDao.removePerson(persona);
	}

	@Override
	public Persona findPersonById(int id) {
		
		return personaDao.findPersonById(id);
	}

}
