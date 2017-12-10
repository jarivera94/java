package com.sga.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sga.eis.PersonaUserDao;
import com.sga.entity.Persona;
import com.sga.entity.User;
@Stateless
public class PersonaUserServiceImpl implements PersonaUserService {
	
	@EJB
	private PersonaUserDao personaDao;
	
	@Override
	public void insertaPersona(Persona persona) {
		// TODO Auto-generated method stub
		personaDao.insertPerson(persona);
	}

	@Override
	public void insertaUser(User user) {
		// TODO Auto-generated method stub
		personaDao.insertUser(user);
	}

	@Override
	public void actualizaPersona(Persona persona) {
		// TODO Auto-generated method stub
		personaDao.updatePersona(persona);
	}

	@Override
	public void actualizaUser(User user) {
		// TODO Auto-generated method stub
		personaDao.updateUser(user);
	}

	@Override
	public void eliminaPersona(Persona persona) {
		// TODO Auto-generated method stub
		personaDao.deletePersona(persona);
	}

	@Override
	public void eliminaUser(User user) {
		// TODO Auto-generated method stub
		personaDao.deleteUser(user);
	}

	@Override
	public List<User> listaUsuarios() {
		// TODO Auto-generated method stub
		return personaDao.listaUsuarios();
	}

	@Override
	public List<Persona> listaPersonas() {
		// TODO Auto-generated method stub
		return personaDao.listaPersonas();
	}

}
