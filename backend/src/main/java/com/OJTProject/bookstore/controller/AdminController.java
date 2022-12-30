package com.OJTProject.bookstore.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.OJTProject.bookstore.dto.OrderDetailsDto;
import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.service.BookService;
import com.OJTProject.bookstore.service.OrderService;
import com.OJTProject.bookstore.service.StorageService;
import com.OJTProject.bookstore.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private OrderService orderService;

//	**********User**********

	@GetMapping("/user/list")
	private ResponseEntity<?> getUserList() {
		List<User> userList = userService.findAll();
		return ResponseEntity.ok().body(userList);
	}

	@GetMapping("/user/status")
	private ResponseEntity<?> getAllStatus() {
		List<String> statusList = userService.getAllStatus();
		return ResponseEntity.ok().body(statusList);
	}

	@PutMapping("/user/status/update")
	private ResponseEntity<?> updateUserStatus(@RequestParam("userId") Long userId,
			@RequestParam("status") String status) {
		User user = userService.updateStatus(userId, status);
		if (user == null) {
			return ResponseEntity.badRequest().body("User is invalid, Status is invalid");
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/user/delete")
	private ResponseEntity<?> deleteUserAccount(@RequestParam("userId") Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		userService.deleteUser(userId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/orders")
	private ResponseEntity<?> getOrders() {
		List<Order> orderList = orderService.findAll();

		return ResponseEntity.ok(orderList);
	}

	@GetMapping("/user/order/detail")
	private ResponseEntity<?> userOrderDetail(@RequestParam("orderId") Long orderId) {

		Order order = orderService.findById(orderId);

		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
		orderDetailsDto.setOrder(order);
		orderDetailsDto.setUserCartItemList(order.getCartItemList());
		orderDetailsDto.setUserOrderAddressList(order.getUserOrderAddressList());
		orderDetailsDto.setUserTransaction(order.getUserTransaction());

		return ResponseEntity.ok().body(orderDetailsDto);

	}

//	**********Book**********

	@GetMapping("/book/list")
	private ResponseEntity<?> bookList() {
		List<Book> bookList = bookService.adminFindAll();

		Path storagePath = Paths.get("").resolve("src").resolve("main").resolve("resources").resolve("static")
				.resolve("images").resolve("books");
		ResponseEntity.ok().body(storagePath);
		return ResponseEntity.ok().body(bookList);
	}

	@PostMapping("/book/add")
	private ResponseEntity<?> addBook(@Valid @RequestBody Book book) throws IOException {
		Book saveBook = bookService.save(book);

		return ResponseEntity.ok().body(saveBook);
	}

	@PostMapping("/book/file/create")
	public String createFile(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
		String fileName = storageService.saveBookImage(file, fileType);
		return fileName;
	}

	@PutMapping("/book/file/update")
	public String updateFile(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType,
			@RequestParam("filePath") String filePath) {
		String fileName = storageService.updateBookImage(file, fileType, filePath);
		return fileName;
	}

	@PutMapping("/book/update")
	public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) {
		Book updateBook = bookService.save(book);
		if (updateBook == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateBook);
	}

	@GetMapping("/book/media/{fileType}/{fileName}")
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

	@GetMapping("book/details/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
		Book book = bookService.findById(id);
		if (book == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(book);
	}

	@DeleteMapping(value = "/book/delete/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		Book book = bookService.findById(id);
		if (book == null) {
			return ResponseEntity.notFound().build();
		}
		String posterPath = book.getPosterPath();
		boolean isDeleted = bookService.removeOne(id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
//		storageService.deleteBookImage("222222_demo.jpg");
		storageService.deleteBookImage(posterPath);
		return ResponseEntity.ok().build();
	}

}
