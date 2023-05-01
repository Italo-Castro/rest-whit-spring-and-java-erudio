package br.com.cci.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cci.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {}