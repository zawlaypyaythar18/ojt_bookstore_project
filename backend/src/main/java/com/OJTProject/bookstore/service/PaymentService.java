package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserTransaction;

public interface PaymentService {

	UserTransaction setByUserPayment(UserPaymentMethod userPaymentMethod, UserTransaction userTransaction);

}
