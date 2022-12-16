package com.OJTProject.bookstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.model.Book;

public interface BookstoreRepo extends JpaRepository<Book, Integer> {

	List<Book> findAll();

}
