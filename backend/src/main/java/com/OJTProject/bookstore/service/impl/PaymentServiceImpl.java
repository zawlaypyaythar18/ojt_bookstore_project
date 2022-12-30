package com.OJTProject.bookstore.service.impl;

import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserTransaction;
import com.OJTProject.bookstore.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public UserTransaction setByUserPayment(UserPaymentMethod userPaymentMethod, UserTransaction userTransaction) {

		userTransaction.setCardName(userPaymentMethod.getCardName());
		userTransaction.setCardNumber(userPaymentMethod.getCardNumber());
		userTransaction.setCardType(userPaymentMethod.getCardType());
		userTransaction.setCvc(userPaymentMethod.getCvc());
		userTransaction.setExpiryMonth(userPaymentMethod.getExpiryMonth());
		userTransaction.setExpiryYear(userPaymentMethod.getExpiryYear());
		userTransaction.setHolderName(userPaymentMethod.getHolderName());

		return userTransaction;
	}

}
