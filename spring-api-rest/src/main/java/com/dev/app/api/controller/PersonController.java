package com.dev.app.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.api.dto.PersonVO;
import com.dev.app.api.dto.PersonVOV2;
import com.dev.app.api.service.PersonService;
import com.dev.app.api.service.PersonaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// Habilitando CORS de forma directa en el controller
//@CrossOrigin(origins = "*")
@Api(value = "Person Endpoint", description = "Description for Person", tags = { "Person Endpoint V1" })
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	@Qualifier("personService")
	private PersonService personService;
	
	@Autowired
	@Qualifier("personaService")
	private PersonaService personaService;
	
	@ApiOperation(value = "Find All People Recorded")
	@RequestMapping(value = "/all", method = RequestMethod.GET, 
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public List<PersonVO> findAll() {
		List<PersonVO> personsVO = personaService.findAll();
		personsVO.stream()
				.forEach((p) -> {
					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
				});
		return personsVO;
		
	}
	
	@ApiOperation(value = "Find a especific Person by your ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET,
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		PersonVO personVO = personaService.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}
	
	@ApiOperation(value = "Create a new Person")
	@RequestMapping(method = RequestMethod.POST, 
					consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" },
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public PersonVO createPerson(@RequestBody PersonVO person) {
		PersonVO personVO = personaService.create(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		personVO.add(linkTo(methodOn(PersonController.class).findAll()).withRel("persons"));
		return personVO;
	}
	
	/**
	 * EJEMPLO DE VERSIONAMIENTO DE API
	 * */
	@PostMapping(value = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVOV2 createPersonV2(@RequestBody PersonVOV2 person) {
		return personaService.createV2(person);
	}
	
	@ApiOperation(value = "Update a specific Person")
	@RequestMapping(method = RequestMethod.PUT, 
					consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" },
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public PersonVO updatePerson(@RequestBody PersonVO person) {
		PersonVO personVO = personaService.update(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		personVO.add(linkTo(methodOn(PersonController.class).findAll()).withRel("persons"));
		return personVO;
	}
	
	@ApiOperation(value = "Delete A Especifi Person by your ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePerson(@PathVariable(value = "id") Long id) {
		personaService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Disabled A Specific Person by your ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH,
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public PersonVO disabledPerson(@PathVariable(value = "id") Long id) {
		PersonVO personVO = personaService.disabledPerson(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}
}