package com.OJTProject.bookstore.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.repo.UserCartRepo;
import com.OJTProject.bookstore.service.UserCartItemService;
import com.OJTProject.bookstore.service.UserCartService;

@Service
@Transactional
public class UserCartServiceImpl implements UserCartService {

	@Autowired
	private UserCartRepo userCartRepo;

	@Autowired
	private UserCartItemService userCartItemService;

	@Override
	public UserCart updateUserCart(UserCart userCart) {

		BigDecimal cartTotal = new BigDecimal(0);

		List<UserCartItem> userCartItemList = userCartItemService.findByUserCart(userCart);

		for (UserCartItem userCartItem : userCartItemList) {
			if (userCartItem.getBook().getInStockNumber() > 0) {
				userCartItemService.updateCartItem(userCartItem);
				cartTotal = cartTotal.add(userCartItem.getSubTotal());
			}
		}

		userCart.setGrandTotal(cartTotal);

		userCartRepo.save(userCart);

		return userCart;
	}

	@Override
	public void clearUserCart(UserCart userCart) {

		List<UserCartItem> userCartItemList = userCartItemService.findByUserCart(userCart);

		for (UserCartItem userCartItem : userCartItemList) {
			userCartItem.setUserCart(null);
			userCartItemService.save(userCartItem);
		}

		userCart.setGrandTotal(new BigDecimal(0));
		userCartRepo.save(userCart);
	}

	@Override
	public UserCart findById(Long userCartId) {
		return userCartRepo.findById(userCartId).orElse(null);
	}


}
