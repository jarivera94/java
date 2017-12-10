package com.spring.service;

import java.util.List;

import com.spring.model.Persona;

public interface IservicePersona {
	public List<Persona> listPersons();
	public boolean saveOrUpdate(Persona persona);
	public boolean removePerson(Persona persona);
	public Persona findPersonById(int id);
}
