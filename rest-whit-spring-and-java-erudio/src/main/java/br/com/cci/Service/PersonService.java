package br.com.cci.Service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cci.exceptions.ResourceNotFoundException;
import br.com.cci.model.Person;
import br.com.repositories.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
		
	@Autowired
	PersonRepository repository;
		
	public List<Person> findAll() {
		
		return repository.findAll();
	}
	
	public Person create(Person person) { 
		logger.info("Creating onde Person");
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Creating onde Person");
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No resource found for this ID!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		
		return repository.save(entity);
	}
	
	public Person findById(Long id) {
			logger.info("Finding one Person");
			
			return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No resource found for this ID!"));
			
	}

	public void delete(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No resource found for this ID!"));
		repository.delete(entity);
	}
}
