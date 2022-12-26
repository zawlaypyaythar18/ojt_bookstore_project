package com.OJTProject.bookstore.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.service.BookService;
import com.OJTProject.bookstore.service.StorageService;
import com.OJTProject.bookstore.service.UserService;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private StorageService storageService;

//	**********Book**********
	@GetMapping("/book")
	public List<Book> findAll() {
		return bookService.findAll();
	}
	
	@GetMapping("/book/details/{id}")
	public  ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
		Book book =  bookService.findById(id);
		if (book == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(book);
	}

	@GetMapping("/book/media/{fileType}/{fileName}")
	public ResponseEntity<byte[]> getPoster(
			@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName
	) throws IOException {
		byte[] fileBytes = storageService.load(fileName);
		MediaType contentType = MediaType.IMAGE_PNG;
		switch (fileType) {
			case "jpg" :
				contentType = MediaType.IMAGE_JPEG;
				break;
			case "png" :
				contentType = MediaType.IMAGE_PNG;
				break;
			default :
				break;
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}
	
}
