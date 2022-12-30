package com.OJTProject.bookstore.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.entity.UserCartItemBook;
import com.OJTProject.bookstore.repo.UserCartItemBookRepo;
import com.OJTProject.bookstore.repo.UserCartItemRepo;
import com.OJTProject.bookstore.service.UserCartItemService;

@Service
@Transactional
public class UserCartItemServiceImpl implements UserCartItemService {

	@Autowired
	private UserCartItemRepo userCartItemRepo;

	@Autowired
	private UserCartItemBookRepo userCartItemBookRepo;

	@Override
	public List<UserCartItem> findByUserCart(UserCart userCart) {
		return userCartItemRepo.findByUserCart(userCart);
	}

	@Override
	public UserCartItem updateCartItem(UserCartItem userCartItem) {

		BigDecimal bigDecimal = new BigDecimal(userCartItem.getBook().getOurPrice())
				.multiply(new BigDecimal(userCartItem.getQty()));

		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

		userCartItem.setSubTotal(bigDecimal);

		userCartItemRepo.save(userCartItem);

		return userCartItem;
	}

	@Override
	public UserCartItem addBookToCartItem(Book book, User user, Integer qty) {

		List<UserCartItem> userCartItemList = userCartItemRepo.findByUserCart(user.getUserCart());

		for (UserCartItem userCartItem : userCartItemList) {
			if (book.getId() == userCartItem.getBook().getId()) {
				userCartItem.setQty(userCartItem.getQty() + qty);
				userCartItem.setSubTotal(
						new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(userCartItem.getQty())));
				userCartItemRepo.save(userCartItem);
				return userCartItem;
			}
		}

		UserCartItem userCartItem = new UserCartItem();

		userCartItem.setUserCart(user.getUserCart());
		userCartItem.setBook(book);
		userCartItem.setQty(qty);
		userCartItem.setSubTotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));

		userCartItem = userCartItemRepo.save(userCartItem);

		UserCartItemBook userCartItemBook = new UserCartItemBook();
		userCartItemBook.setBook(book);
		userCartItemBook.setCartItem(userCartItem);
		userCartItemBookRepo.save(userCartItemBook);

		return userCartItem;
	}

	@Override
	public UserCartItem findById(Long cartItemId) {
		return userCartItemRepo.findById(cartItemId).orElse(null);
	}

	@Override
	public UserCartItem save(UserCartItem userCartItem) {
		return userCartItemRepo.save(userCartItem);
	}

	@Override
	public void removeCartItem(UserCartItem userCartItem) {
		userCartItemBookRepo.deleteByCartItem(userCartItem);
		userCartItemRepo.delete(userCartItem);
	}

}
