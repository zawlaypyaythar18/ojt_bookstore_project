package com.OJTProject.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.service.BookService;
import com.OJTProject.bookstore.service.UserCartItemService;
import com.OJTProject.bookstore.service.UserCartService;
import com.OJTProject.bookstore.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserCartItemService userCartItemService;

	@Autowired
	private UserCartService userCartService;

	@Autowired
	private BookService bookService;

	@GetMapping("/item/list")
	private ResponseEntity<?> userCart(@RequestParam("userId") Long userId) {

		User user = userService.findById(userId);

		UserCart userCart = user.getUserCart();

		List<UserCartItem> userCartItemList = userCartItemService.findByUserCart(userCart);

		userCartService.updateUserCart(userCart);

		return ResponseEntity.ok().body(userCartItemList);
	}

	@PostMapping("/item/add")
	private ResponseEntity<?> addItem(@RequestParam("userId") Long userId, @RequestParam("bookId") Long bookId,
			@RequestParam("qty") int qty) {
		User user = userService.findById(userId);

		Book book = bookService.findById(bookId);

		if (qty > book.getInStockNumber()) {
			return ResponseEntity.badRequest().build();
		}

		userCartItemService.addBookToCartItem(book, user, qty);
		userCartService.updateUserCart(user.getUserCart());

		return ResponseEntity.ok().body(book);
	}

	@PutMapping("/item/update")
	private ResponseEntity<?> updateCartItem(@RequestParam("cartItemId") Long cartItemId,
			@RequestParam("qty") Integer qty) {
		UserCartItem userCartItem = userCartItemService.findById(cartItemId);

		userCartItem.setQty(qty);

		userCartItemService.updateCartItem(userCartItem);

		userCartService.updateUserCart(userCartItem.getUserCart());

		return ResponseEntity.ok().body(userCartItem);

	}

	@DeleteMapping("/item/remove")
	private ResponseEntity<?> removeCartItem(@RequestParam("cartItemId") Long cartItemId) {
		UserCartItem userCartItem = userCartItemService.findById(cartItemId);

		userCartItemService.removeCartItem(userCartItem);

		userCartService.updateUserCart(userCartItem.getUserCart());

		return ResponseEntity.ok().build();
	}

}
