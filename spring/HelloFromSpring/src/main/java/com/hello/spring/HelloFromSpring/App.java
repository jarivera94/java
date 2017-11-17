package com.hello.spring.HelloFromSpring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hello.spring.beans.HelloFromCountry;
import com.hello.spring.configuration.AppConfig;

public class App {
    public static void main( String[] args ){
    	//used file xml
    	//ApplicationContext aplicationContext = new ClassPathXmlApplicationContext("com/hello/xml/beans.xml");
    	
    	//used java annotations
    	AnnotationConfigApplicationContext aplicationContext= new AnnotationConfigApplicationContext(AppConfig.class);
    	
    	
    	HelloFromCountry helloWorld = (HelloFromCountry) aplicationContext.getBean("hello");
    	System.out.println(helloWorld.getSaluda());
    	
    	((ConfigurableApplicationContext)aplicationContext).close();
    }
}
