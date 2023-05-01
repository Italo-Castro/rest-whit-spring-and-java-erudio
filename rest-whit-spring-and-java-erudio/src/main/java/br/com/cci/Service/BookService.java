package br.com.cci.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cci.controller.BookController;

import br.com.cci.data.vo.v1.BookVO;
import br.com.cci.exceptions.RequiredObjectIsNullException;
import br.com.cci.exceptions.ResourceNotFoundException;
import br.com.cci.mapper.DozerMapper;

import br.com.cci.model.Book;
import br.com.cci.repositories.BookRepository;

@Service
public class BookService {

	
	private Logger logger = Logger.getLogger(Book.class.getName());
	
	
	@Autowired
	BookRepository repository;

	
	public List<BookVO> findAll() {
		logger.info("Finding all books");
		
		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		
		//adicionando hateoas
		books.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getId())).withSelfRel()));
		return books;
	}
	
	
	public BookVO findById(Long id) {
		logger.info("Finding one BookVO!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return vo;
	}

	
	public BookVO update(BookVO book) {
		if (book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one book!");
		
		
		var entity = repository.findById(book.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunch_date(book.getLaunch_date());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());
		
		return vo;
		
	}
	
		public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
