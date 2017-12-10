package com.sga.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sga.entity.Persona;
import com.sga.entity.User;

@Stateless
public class PerssonaUserDaoImpl implements PersonaUserDao {

	@PersistenceContext(unitName="recursos_humanos")
	private EntityManager em;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> listaUsuarios() {
		List<User>users = em.createNativeQuery("SELECT * FROM User").getResultList();
		return users;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Persona> listaPersonas() {
		List<Persona>personas=em.createNamedQuery("Persona.findAll").getResultList();
		for (Persona persona : personas) {
			System.out.println(persona.getApellido1());
		}
		return personas;
	}

	@Override
	public void updatePersona(Persona persona) {
		em.merge(persona);
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public void deletePersona(Persona persona) {
		em.merge(persona);
		em.remove(persona);
	}

	@Override
	public void deleteUser(User user) {
		em.merge(user);
		em.remove(user);

	}

	@Override
	public void insertUser(User user) {
		em.persist(user);
	}

	@Override
	public void insertPerson(Persona persona) {
		em.persist(persona);
	}

}
