package br.com.cci.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cci.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {}