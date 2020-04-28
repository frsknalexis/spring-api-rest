package com.dev.app.api.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import com.dev.app.api.controller.PersonController;
import com.dev.app.api.converter.DozerConverter;
import com.dev.app.api.dto.PersonVO;
import com.dev.app.api.model.Person;

@Component
public class PersonModelAssembler extends RepresentationModelAssemblerSupport<Person, PersonVO> {

	public PersonModelAssembler() {
		super(PersonController.class, PersonVO.class);
	}

	@Override
	public PersonVO toModel(Person entity) {
		PersonVO personVO = DozerConverter.parseObject(entity, PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}
	
	@Override
	public CollectionModel<PersonVO> toCollectionModel(Iterable<? extends Person> entities) {
		CollectionModel<PersonVO> personsModel = super.toCollectionModel(entities);
		personsModel.add(linkTo(methodOn(PersonController.class).findAll()).withRel("persons"));
		personsModel.forEach((p) -> {
			p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
		});
		return personsModel;
	}
}
