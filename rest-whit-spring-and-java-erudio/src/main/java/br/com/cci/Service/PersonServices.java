package br.com.cci.Service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cci.data.vo.v1.PersonVO;
import br.com.cci.data.vo.v2.PersonVO2;
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
		
		return DozerMapper.parseListObjects(repository.findAll(),PersonVO.class);
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	 
	public PersonVO create(PersonVO person) {

		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	} 
	 
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
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
