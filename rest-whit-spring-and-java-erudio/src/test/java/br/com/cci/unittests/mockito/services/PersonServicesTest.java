package br.com.cci.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.cci.Service.PersonServices;
import br.com.cci.data.vo.v1.PersonVO;
import br.com.cci.exceptions.RequiredObjectIsNullException;
import br.com.cci.model.Person;
import br.com.cci.repositories.PersonRepository;
import br.com.cci.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	MockPerson input;
	
	
	@InjectMocks
	private PersonServices service;
	
	@Mock
	PersonRepository repository;
		
	
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getkey());
		assertNotNull(result.getLinks());
		
		System.out.println("reult.toString "+ result.toString());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}
	
	@Test
	void testCreate() {
		Person entity = input.mockEntity(1);
		Person persisted = entity;
		
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setkey(1L);
		
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getkey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}
	
	@Test
	void testCreateWithNullPerson() {
	
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
	
		String expectedMessage = "IT IS NOT ALLOWED TO PERSIST A NULL OBJECT";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setkey(1L);
		
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getkey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testUpdateWithNullPerson() {
	
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
	
		String expectedMessage = "IT IS NOT ALLOWED TO PERSIST A NULL OBJECT";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
		
		
	}
	
	@Test
	void testDelete() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll();
		assertNotNull(people);
		assertEquals(14,people.size());
		
		var personOne = people.get(0);	
		assertNotNull(personOne);
		assertNotNull(personOne.getkey());
		assertNotNull(personOne.getLinks());
		assertNotNull(personOne.toString().contains("links: [</api/person/v1/0>;rel=\"self\"]"));
		
		assertEquals("Addres Test0", personOne.getAddress());
		assertEquals("First Name Test0", personOne.getFirstName());
		assertEquals("Last Name Test0", personOne.getLastName());
		assertEquals("Male", personOne.getGender());
		
		
		
		var personFour = people.get(4);	
		assertNotNull(personFour);
		assertNotNull(personFour.getkey());
		assertNotNull(personFour.getLinks());
		assertNotNull(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
		
		assertEquals("Addres Test4", personFour.getAddress());
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("Male", personFour.getGender());
		
		
		var personSeven = people.get(7);	
		assertNotNull(personSeven);
		assertNotNull(personSeven.getkey());
		assertNotNull(personSeven.getLinks());
		assertNotNull(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
		
		assertEquals("Addres Test7", personSeven.getAddress());
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("Female", personSeven.getGender());
		
		
		
		
	}

	
}
