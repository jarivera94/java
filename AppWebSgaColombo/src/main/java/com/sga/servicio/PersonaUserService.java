package com.sga.servicio;

import java.util.List;

import javax.ejb.Local;

import com.sga.entity.Persona;
import com.sga.entity.User;

@Local
public interface PersonaUserService {

	public void insertaPersona(Persona persona);
	public void insertaUser(User user);
	
	public void actualizaPersona(Persona persona);
	public void actualizaUser(User user);
	
	public void eliminaPersona(Persona persona);
	public void eliminaUser(User user);
	
	public List<User> listaUsuarios();
	public List<Persona> listaPersonas();
	
}
