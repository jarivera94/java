package com.spring.dao;

import java.util.List;

import com.spring.model.Persona;

public interface IpersonaDao {

	public List<Persona> listPersons();
	public boolean saveOrUpdate(Persona persona);
	public boolean removePerson(Persona persona);
	public Persona findPersonById(int id);
}
