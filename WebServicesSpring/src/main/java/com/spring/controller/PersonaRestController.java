package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.model.Persona;
import com.spring.service.IservicePersona;

@RestController
public class PersonaRestController {

	private static String resultado;
	
	@Autowired
	private IservicePersona servicePersona;

	@GetMapping("/personas")
	public String listPersona() {
		Gson gson = new Gson();
		resultado = gson.toJson(servicePersona.listPersons());
		return resultado;
	}
	
	@PostMapping("/add")
	public boolean addPerson(@RequestBody String peticion){
		Gson gson = new Gson();
		Persona persona =gson.fromJson(peticion, Persona.class);
		return  servicePersona.saveOrUpdate(persona);
	}
	
	@PutMapping("/update")
	public boolean updatePerson(@RequestBody String peticion){
		Gson gson = new Gson ();
		Persona persona = gson.fromJson(peticion, Persona.class);
		return servicePersona.saveOrUpdate(persona);
	}
	
	@DeleteMapping("/delete/{personId}")
	public boolean deletePerson(@PathVariable int personId){
		Persona persona = servicePersona.findPersonById(personId);
		return servicePersona.removePerson(persona);
	}
	
	@GetMapping("/persona/{personId}")
	public String findPerson(@PathVariable int personId){
		Gson gson = new Gson();
		return gson.toJson(servicePersona.findPersonById(personId));
	} 

}
