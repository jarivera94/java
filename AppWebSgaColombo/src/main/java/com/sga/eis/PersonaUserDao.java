package com.sga.eis;

import java.util.List;

import javax.ejb.Local;

import com.sga.entity.Persona;
import com.sga.entity.User;

@Local
public interface PersonaUserDao {

	public List<User> listaUsuarios();
	public List<Persona> listaPersonas();
	
	public void updatePersona(Persona persona);
	public void updateUser(User user);
	
	public void deletePersona(Persona persona);
	public void deleteUser(User user);
	
	public void insertUser(User user);
	public void insertPerson(Persona persona);
}
