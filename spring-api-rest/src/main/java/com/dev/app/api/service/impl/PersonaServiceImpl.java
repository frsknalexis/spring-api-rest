package com.dev.app.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.app.api.controller.exception.ResourceNotFoundException;
import com.dev.app.api.converter.DozerConverter;
import com.dev.app.api.converter.custom.PersonConverter;
import com.dev.app.api.dto.PersonVO;
import com.dev.app.api.dto.PersonVOV2;
import com.dev.app.api.model.Person;
import com.dev.app.api.repository.PersonRepository;
import com.dev.app.api.service.PersonaService;

@Service("personaService")
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	@Qualifier("personRepository")
	private PersonRepository personRepository;

	@Override
	public PersonVO create(PersonVO person) {
		Person entityPerson = DozerConverter.parseObject(person, Person.class);
		Person entityPersonResponse = personRepository.save(entityPerson);
		return DozerConverter.parseObject(entityPersonResponse, PersonVO.class);
	}
	
	@Override
	public PersonVOV2 createV2(PersonVOV2 person) {
		Person entityPerson = PersonConverter.convertVOToEntity(person);
		Person entityPersonResponse = personRepository.save(entityPerson);
		return PersonConverter.convertEntityToVO(entityPersonResponse);
	}

	@Override
	public List<PersonVO> findAll() {
		List<Person> entityPersonList = personRepository.findAll();
		return DozerConverter.parseListObject(entityPersonList, PersonVO.class);
	}

	@Override
	public Page<PersonVO> findAll(Pageable pageable) {
		Page<Person> entityList = personRepository.findAll(pageable);
		return entityList.map((p) -> DozerConverter.parseObject(p, PersonVO.class));
	}
	
	@Override
	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
		Page<Person> entityList = personRepository.findPersonByName(firstName, pageable);
		return entityList.map((p) -> DozerConverter.parseObject(p, PersonVO.class));
	}
	
	@Override
	public PersonVO findById(Long id) {
		Person entityPerson = personRepository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entityPerson, PersonVO.class);
	}

	@Override
	public PersonVO update(PersonVO person) {
		Person entity = personRepository.findById(person.getKey())
								.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		Person entityPersonResponse = personRepository.save(entity);
		return DozerConverter.parseObject(entityPersonResponse, PersonVO.class);
	}

	@Override
	public void delete(Long id) {
		Person entity = personRepository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		personRepository.delete(entity);
	}

	@Transactional
	@Override
	public PersonVO disabledPerson(Long id) {
		personRepository.disabledPerson(id);
		Person entity = personRepository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
}