package com.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name="User.findAll",query="SELECT u FROM Usuario u")})
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;

	public Usuario(String username, String password, Persona persona) {
		super();
		this.username = username;
		this.password = password;
		this.persona = persona;
	}

	public Usuario(int id, String username, String password, Persona persona) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.persona = persona;
	}

	public Usuario(int id ) {
		this.id = id ;
	}

	public Usuario() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", username=" + username + ", password=" + password 
				+ "]";
	}

}
