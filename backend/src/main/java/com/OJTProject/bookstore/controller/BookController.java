package com.OJTProject.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.model.Book;
import com.OJTProject.bookstore.service.BookstoreServiceInter;

    @RestController
    @RequestMapping("/api")
    public class BookController{
	@Autowired
	
	BookstoreServiceInter service;
	
	@GetMapping("/book")
	List <Book> getall () {
		return service.getAll();
	}
	
	@GetMapping("/book/{book_id}")
	Book get (@PathVariable("book_id") int bookID){
		return service.get(bookID);
	}
	
	@PostMapping("/book")
	Book create (@RequestBody Book book){
		return service.create(book);
	}
	
	@PutMapping("/book")
	ResponseEntity<?> update (@RequestBody Book book){
		Book updatedBook= service.update(book);
		if (updatedBook==null) {
			return ResponseEntity.badRequest().body("Book Cannot be updated");
		}
		return ResponseEntity.ok(updatedBook);
	}
	
	@DeleteMapping("/book")
	boolean delete (@RequestBody  Book book){
		return service.delete(book.getId());
	}
//	public ResponseEntity<String> home() {
//		return ResponseEntity.ok("Hello World");
//	}

}
