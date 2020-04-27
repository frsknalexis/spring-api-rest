package com.dev.app.api.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dev.app.api.model.Person;
import com.dev.app.api.service.PersonService;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	private final AtomicLong counter = new AtomicLong();
	
	@Override
	public Person findById(String id) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Leandro");
		person.setLastName("Costa");
		person.setAddress("Uberlândia - Minas Gerais -Brasil");
		person.setGender("Male");
		return person;
	}

	@Override
	public List<Person> findAll() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		List<Person> personList = integers.stream()
							.map((i) -> {
								return mockPerson(i);
							})
							.collect(Collectors.toList());
		return personList;
	}
	
	private Person mockPerson(Integer i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Person name" + i);
		person.setLastName("Last name" + i);
		person.setAddress("Some address in Brasil" + i);
		person.setGender("Male");
		return person;
	}

	@Override
	public Person create(Person person) {
		return person;
	}

	@Override
	public Person update(Person person) {
		return person;
	}

	@Override
	public void delete(String id) {
		
	}
}