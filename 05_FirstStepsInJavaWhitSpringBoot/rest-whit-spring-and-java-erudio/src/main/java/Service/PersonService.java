package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import model.Person;

@Service
public class PersonService {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	
	public List<Person> findAll() {
		List<Person> persons = new ArrayList<>();
		for (int i =0; i< 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		
		return persons;
	}
	
	public Person mockPerson(int i) {

		Person person = new Person();
		
		person.setId(counter.incrementAndGet());
		person.setFirstName("Italo");
		person.setLastName("Castro");
		person.setAddress("Formiga");
		
		
		return person;
	}
	
	public Person create(Person person) {
		logger.info("Creating onde Person");
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Creating onde Person");
		return person;
	}
	
	public Person findById(String id) {
			logger.info("Finding one Person");
			
			Person person = new Person();
			
			person.setId(counter.incrementAndGet());
			person.setFirstName("Italo");
			person.setLastName("Castro");
			person.setAddress("Formiga");
			
			
			return person;
			
	}
}
