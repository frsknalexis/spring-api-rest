package com.dev.app.api.service;

import java.util.List;

import com.dev.app.api.model.Person;

public interface PersonService {

	public Person findById(String id);
	
	public List<Person> findAll();
	
	public Person create(Person person);
	
	public Person update(Person person);
	
	public void delete(String id);
}
