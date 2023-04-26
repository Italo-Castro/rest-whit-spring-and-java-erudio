package br.com.cci.Service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.stereotype.Service;

import br.com.cci.controller.PersonController;
import br.com.cci.data.vo.v1.PersonVO;
import br.com.cci.data.vo.v2.PersonVO2;
import br.com.cci.exceptions.RequiredObjectIsNullException;
import br.com.cci.exceptions.ResourceNotFoundException;
import br.com.cci.mapper.DozerMapper;
import br.com.cci.mapper.custom.PersonMapper;
import br.com.cci.model.Person;
import br.com.cci.repositories.PersonRepository;


@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;

	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {

		logger.info("Finding all people!");
		
		var persons = DozerMapper.parseListObjects(repository.findAll(),PersonVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getkey())).withSelfRel()));
		
		return persons;
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo =  DozerMapper.parseObject(entity, PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	 
	public PersonVO create(PersonVO person) {
		
		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getkey())).withSelfRel());
		
		return vo;
	} 
	 
	
	public PersonVO update(PersonVO person) {
		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getkey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		 var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		 vo.add(linkTo(methodOn(PersonController.class).findById(vo.getkey())).withSelfRel());
		 return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
	
	public PersonVO2 createV2(PersonVO2 person) {

		logger.info("Creating one person whit V2!");
		
		var entity = mapper.converVoToEntity(person);
		return mapper.converEntityToVo(repository.save(entity));
	} 
	 
}
