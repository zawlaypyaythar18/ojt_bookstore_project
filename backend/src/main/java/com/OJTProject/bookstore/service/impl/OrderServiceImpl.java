package com.OJTProject.bookstore.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserTransaction;
import com.OJTProject.bookstore.repo.OrderRepo;
import com.OJTProject.bookstore.service.OrderService;
import com.OJTProject.bookstore.service.UserCartItemService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private UserCartItemService userCartItemService;

	@Override
	public Order findById(Long orderId) {
		return orderRepo.findById(orderId).orElse(null);
	}

	@Override
	public Order createOrder(UserCart userCart, List<UserOrderAddress> userOrderAddressList,
			UserTransaction userTransaction, String shippingMethod, User user) {

		Order order = new Order();
		order.setUserOrderAddressList(userOrderAddressList);
		order.setUserTransaction(userTransaction);
		order.setShippingMethod(shippingMethod);
		order.setOrderStatus("created");

		LocalDate today = LocalDate.now();

		LocalDate estimatedDeliveryDate;

		if (shippingMethod.equals("groundShipping")) {
			estimatedDeliveryDate = today.plusDays(5);
		} else {
			estimatedDeliveryDate = today.plusDays(3);
		}

		order.setShippingDate(estimatedDeliveryDate);
//		order.setShippingDate(Date.from(estimatedDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

		List<UserCartItem> userCartItemList = userCartItemService.findByUserCart(userCart);

		for (UserCartItem cartItem : userCartItemList) {
			Book book = cartItem.getBook();
			cartItem.setOrder(order);
			book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
		}

		order.setCartItemList(userCartItemList);
//		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderDate(today);
		order.setOrderTotal(userCart.getGrandTotal());

		for (UserOrderAddress orderAddress : userOrderAddressList) {
			orderAddress.setOrder(order);
		}

		userTransaction.setOrder(order);
		order.setUser(user);

		order = orderRepo.save(order);

		return order;
	}

	@Override
	public List<Order> findAll() {
		return orderRepo.findAll();
	}

}
