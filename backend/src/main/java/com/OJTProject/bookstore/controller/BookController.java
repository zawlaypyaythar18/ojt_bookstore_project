package com.OJTProject.bookstore.controller;

<<<<<<< HEAD
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.service.BookService;
import com.OJTProject.bookstore.service.StorageService;

@RestController
@RequestMapping("/api/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;

	@GetMapping("/list")
	private ResponseEntity<?> bookList() {
		List<Book> bookList = bookService.findAll();

		Path storagePath = Paths.get("").resolve("src").resolve("main").resolve("resources").resolve("static")
				.resolve("images").resolve("books");
		ResponseEntity.ok().body(storagePath);
		return ResponseEntity.ok().body(bookList);
	}

	@GetMapping("/details/{book_id}")
	public ResponseEntity<Book> getBook(@PathVariable("book_id") Long bookID) {
		Book book = bookService.findById(bookID);
		if (book == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(book);
	}

	@GetMapping("/category/search")
	private ResponseEntity<?> findByCategory(@RequestParam("category") String category,
			@RequestParam("noPage") int noPage, @RequestParam("count") int count) {
		Pageable pageable = PageRequest.of(noPage, count);
		if (category.equals("All Books")) {
			Page<Book> pageAllBooks = bookService.findByPage(pageable);
			return ResponseEntity.ok(pageAllBooks);
		}
		Page<Book> pageCategoryList = bookService.findByCategoryWithPage(category, pageable);

		if (pageCategoryList.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(pageCategoryList);
	}

	@GetMapping("/page/search")
	private ResponseEntity<?> pagination(@RequestParam("noPage") int noPage, @RequestParam("count") int count) {
		Pageable pageable = PageRequest.of(noPage, count);
		Page<Book> pageBooks = bookService.findByPage(pageable);

		Path storagePath = Paths.get("").resolve("src").resolve("main").resolve("resources").resolve("static")
				.resolve("images").resolve("books");

		ResponseEntity.ok().body(storagePath);
		return ResponseEntity.ok().body(pageBooks);
	}

	@GetMapping("/media/{fileType}/{fileName}")
	public ResponseEntity<byte[]> getPoster(@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName) throws IOException {
		byte[] fileBytes = storageService.load(fileName);
		MediaType contentType = MediaType.IMAGE_PNG;
		switch (fileType) {
		case "jpg":
			contentType = MediaType.IMAGE_JPEG;
			break;
		case "png":
			contentType = MediaType.IMAGE_PNG;
			break;
		default:
			break;
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}

	@RequestMapping("/title/search")
	private ResponseEntity<?> findByTitle(@RequestParam("title") String title) {
		List<Book> titleList = bookService.blurrySearch(title);
		return ResponseEntity.ok(titleList);
=======
import java.time.LocalDateTime;
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
	
	@GetMapping("/book/get/{book_id}")
	Book get (@PathVariable("book_id") int bookID){
		return service.get(bookID);
	}
	
	@PostMapping("/book/add")
	Book create (@RequestBody Book book){

		return service.create(book);
	}
	
	@PutMapping("/book/update")
	ResponseEntity<?> update (@RequestBody Book book){
		Book updatedBook= service.update(book);
		if (updatedBook==null) {
			return ResponseEntity.badRequest().body("Book Cannot be updated");
		}
		return ResponseEntity.ok(updatedBook);
	}
	
	@DeleteMapping("/book/delete/{id}")
	ResponseEntity<?> delete (@PathVariable int id){
		boolean isDelected = service.delete(id);
		if (isDelected==false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
>>>>>>> a02a108b4c17875e79c5edd1b8d86399a1c28e78
	}

}
