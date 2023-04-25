package br.com.cci.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.cci.data.vo.v2.PersonVO2;
import br.com.cci.model.Person;

@Service	
public class PersonMapper {

	
	
	public PersonVO2 converEntityToVo(Person person) {
		PersonVO2 vo = new PersonVO2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		
		return vo;
	}
	
	public Person converVoToEntity(PersonVO2 vo) {
		Person person = new Person();
		person.setId(vo.getId());
		person.setAddress(vo.getAddress());
		person.setFirstName(vo.getFirstName());
		person.setLastName(vo.getLastName());
		person.setGender(vo.getGender());
		
		return person;
	}
	
}
