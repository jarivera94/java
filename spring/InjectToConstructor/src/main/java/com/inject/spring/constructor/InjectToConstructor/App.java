package com.inject.spring.constructor.InjectToConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inject.spring.constructor.beans.Person;


public class App {
    public static void main( String[] args ){
        ApplicationContext aplicationContext = new ClassPathXmlApplicationContext("com/inject/xml/beans.xml");
        Person person =(Person) aplicationContext.getBean("person");
        System.out.println(person.toString());
        ((ConfigurableApplicationContext)aplicationContext).close();
        
    }
}