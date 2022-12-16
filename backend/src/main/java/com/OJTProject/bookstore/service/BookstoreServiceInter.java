package com.OJTProject.bookstore.service;

import java.util.List;

import com.OJTProject.bookstore.model.Book;

public interface BookstoreServiceInter {
	
	public List<Book> getAll();
	
	public Book get(int id);
	
	public Book create(Book book);
	
	public Book update(Book book);

	public boolean delete(int id);

}
