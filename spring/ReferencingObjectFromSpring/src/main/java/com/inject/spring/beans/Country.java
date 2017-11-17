package com.inject.spring.beans;

public class Country {

	
	private String name;
	private String years;
	private City city;
	
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Country [name=" + name + ", years=" + years + ", city=" + city + "]";
	}
	
}
