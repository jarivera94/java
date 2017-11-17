package com.hello.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hello.spring.beans.HelloFromCountry;

@Configuration
public class AppConfig {

	@Bean
	public HelloFromCountry hello() {
		return new HelloFromCountry(); 
	}
}
