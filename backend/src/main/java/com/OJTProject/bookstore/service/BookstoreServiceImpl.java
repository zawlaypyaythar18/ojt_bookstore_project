package com.OJTProject.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.model.Book;
import com.OJTProject.bookstore.repo.BookstoreRepo;

@Service
public class BookstoreServiceImpl implements BookstoreServiceInter{

	@Autowired
	BookstoreRepo repo;
	
	@Override
	public List<Book> getAll() {
		return repo.findAll();
	}

	@Override
	public Book get(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Book create(Book book) {
		return repo.save(book);
	}

	@Override
	public Book update(Book book) {
		Book findBook = repo.findById(book.getId()).orElse(null);
		if (findBook == null){
			return null;
		}
		return repo.save(book);
	}

	@Override
	public boolean delete(int id) {
		Book findBook = repo.findById(id).orElse(null);
		if (findBook == null){
			return false;
		}
		repo.deleteById(id);
		return true;
	}

}
