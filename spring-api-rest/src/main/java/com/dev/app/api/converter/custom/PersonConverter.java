package com.dev.app.api.converter.custom;

import java.util.Date;

import com.dev.app.api.dto.PersonVOV2;
import com.dev.app.api.model.Person;

public class PersonConverter {

	public static PersonVOV2 convertEntityToVO(Person entity) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(entity.getId());
		vo.setFirstName(entity.getFirstName());
		vo.setLastName(entity.getLastName());
		vo.setAddress(entity.getAddress());
		vo.setGender(entity.getGender());
		vo.setBirthDay(new Date());
		return vo;
	}
	
	public static Person convertVOToEntity(PersonVOV2 personVO) {
		Person person = new Person();
		person.setId(personVO.getId());
		person.setFirstName(personVO.getFirstName());
		person.setLastName(personVO.getLastName());
		person.setAddress(personVO.getAddress());
		person.setGender(personVO.getGender());
		return person;
	}
}
