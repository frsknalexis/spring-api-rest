package com.dev.app.api.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

//CUSTOM JSON SERIALIZATION
@JsonPropertyOrder({"id", "address", "firstName", "lastName", "gender", "enabled"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7408978281656610797L;

	@JsonProperty(value = "id")
	@Mapping("id")
	private Long key;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String gender;
	
	private Boolean enabled;
	
	public PersonVO() {
		
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}