package com.inject.spring.constructor.beans;

public class Person {

	
	private int id;
	
	private String name;
	
	private String lastName;
	
	public Person(int id, String name, String lastName) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
	}
	public Person(int id) {
		super();
		this.id = id;
	}
	public Person(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", lastName=" + lastName + "]";
	}
}
