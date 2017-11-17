package com.inject.spring_reference.objects.ReferencingObjectFromSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inject.spring.beans.Country;


public class App {
    public static void main( String[] args ){
        
    	
    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/spring/xml/beans.xml");
    	
    	Country country=(Country ) applicationContext.getBean("country");
    	System.out.println(country.toString()+country.getCity().getName());
    	
    	((ConfigurableApplicationContext)applicationContext).close();
    }
}
