package com.dev.app.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.app.api.model.Person;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {

}
