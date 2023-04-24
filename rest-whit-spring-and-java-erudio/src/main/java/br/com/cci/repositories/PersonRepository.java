package br.com.cci.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cci.model.Person;



@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}