package com.OJTProject.bookstore.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.service.BookService;
import com.OJTProject.bookstore.service.StorageService;

@RestController
@RequestMapping("/api")
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;

	@GetMapping("/book/list")
	private ResponseEntity<?> bookList() {
		List<Book> bookList = bookService.findAll();

		Path storagePath = Paths.get("").resolve("src").resolve("main").resolve("resources").resolve("static")
				.resolve("images").resolve("books");
		ResponseEntity.ok().body(storagePath);
		return ResponseEntity.ok().body(bookList);
	}

	@GetMapping("/book/get/{book_id}")
	public ResponseEntity<Book> getBook(@PathVariable("book_id") Long bookID) {
		Book book = bookService.findById(bookID);
		if (book == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(book);
	}

	@GetMapping("/category/search/{category}")
	public ResponseEntity<?> getBookByCategory(@PathVariable("category") String category) {
		List<Book> bookcat = bookService.findByCategory(category);
		if (category == null) {
			return ResponseEntity.badRequest().body("Category is invalid");
		}
		List<Book> bookList = bookService.findByCategory(category);
		return ResponseEntity.ok().body(bookList);
	}

}
