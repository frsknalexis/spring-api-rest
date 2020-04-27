package com.dev.app.api.service;

import java.util.List;

import com.dev.app.api.dto.PersonVO;
import com.dev.app.api.dto.PersonVOV2;

public interface PersonaService {

	PersonVO create(PersonVO person);
	
	PersonVOV2 createV2(PersonVOV2 person);
	
	List<PersonVO> findAll();
	
	PersonVO findById(Long id);
	
	PersonVO update(PersonVO person);
	
	void delete(Long id);
}
