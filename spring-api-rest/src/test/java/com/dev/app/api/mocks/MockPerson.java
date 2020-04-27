package com.dev.app.api.mocks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dev.app.api.dto.PersonVO;
import com.dev.app.api.model.Person;

public class MockPerson {

	public Person mockEntity() {
    	return mockEntity(0);
    }
    
    public PersonVO mockVO() {
    	return mockVO(0);
    }
    
    public List<Person> mockEntityList() {
    	List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    	List<Person> persons = integerList.stream()
    						.map((i) -> mockEntity(i))
    						.collect(Collectors.toList());
    	return persons;
    }

    public List<PersonVO> mockVOList() {
    	List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    	List<PersonVO> personsVO = integerList.stream()
    						.map((i) -> mockVO(i))
    						.collect(Collectors.toList());
    	return personsVO;
    }
    
	private Person mockEntity(Integer number) {
    	Person person = new Person();
    	person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    private PersonVO mockVO(Integer number) {
    	PersonVO person = new PersonVO();
    	person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setKey(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }
}