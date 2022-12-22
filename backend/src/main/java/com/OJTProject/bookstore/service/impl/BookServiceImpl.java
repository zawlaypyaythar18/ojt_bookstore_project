package com.OJTProject.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.repo.BookRepo;
import com.OJTProject.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepo bookRepo;

	@Override
	public List<Book> findAll() {
		List<Book> bookList = bookRepo.findAll();
		
		List<Book> activeBookList = new ArrayList<>();
		
		for (Book book : bookList) {
			if (book.getActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}

	@Override
	public Book findById(Long id) {
		return bookRepo.findById(id).get();
	}

	@Override
	public List<Book> findByCategory(String category) {
		List<Book> bookList = bookRepo.findByCategory(category);
		
		List<Book> activeBookList = new ArrayList<>();
		
		for (Book book : bookList) {
			if (book.getActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}

	@Override
	public List<Book> blurrySearch(String title) {
		List<Book> bookList = bookRepo.findByTitleContaining(title);
		
		List<Book> activeBookList = new ArrayList<>();
		
		for (Book book : bookList) {
			if (book.getActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}

	@Override
	public List<Book> findTop3ByOrderByIdAsc() {
		return bookRepo.findTop3ByOrderByIdAsc();
	}

	@Override
	public Book save(Book book) {
		return bookRepo.save(book);
	}
	
	@Override
	public boolean removeOne(Long id) {
		Book book = findById(id);
		if (book != null) {
			bookRepo.deleteById(id);
			return true;
		}else { 
			return false;
		}
		
	}

	@Override
	public List<Book> adminFindAll() {
		
		List<Book> bookList = bookRepo.findAll();
		
		return bookList;
	}

	@Override
	public Page<Book> findByPage(Pageable pageable) {
		return bookRepo.findAllByOrderByTitleAsc(pageable);
	}

	@Override
	public Page<Book> findByCategoryWithPage(String category, Pageable pageable) {
		
	List<Book> bookList = bookRepo.findByCategory(category);		
			
			for (Book book : bookList) {
				if (book.getActive()) {
					Page<Book> categoryBookList = bookRepo.findByCategory(category,pageable);
					return categoryBookList;
				}
			}
			return null;
	}


}
