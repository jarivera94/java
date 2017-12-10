package com.sga.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.sga.entity.Persona;
import com.sga.servicio.PersonaUserService;

@ManagedBean
@RequestScoped
public class PersonaUserBean {
	@EJB
	private PersonaUserService personaUserService;
	
	private List<Persona> personas;
	
	public PersonaUserBean(){}
	
	@PostConstruct
	public void init(){
		System.out.println("hola");
		this.personas=personaUserService.listaPersonas();
		
	}

	public PersonaUserService getPersonaUserService() {
		return personaUserService;
	}

	public void setPersonaUserService(PersonaUserService personaUserService) {
		this.personaUserService = personaUserService;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}
}
