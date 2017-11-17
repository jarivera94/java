package com.hello.spring.beans;

import org.springframework.beans.factory.annotation.Value;

public class HelloFromCountry {
	
	@Value("Hello from other country")
	private String saluda;

	public String getSaluda() {
		return saluda;
	}

	public void setSaluda(String saluda) {
		this.saluda = saluda;
	}
}
