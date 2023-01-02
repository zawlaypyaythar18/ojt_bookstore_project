package com.OJTProject.bookstore.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.OJTProject.bookstore.entity.Book;

public interface BookService {

	List<Book> findAll();

	List<Book> adminFindAll();

	Book findById(Long id);

	List<Book> findByCategory(String category);

	Page<Book> blurrySearch(String title, Pageable pageable);

	List<Book> findTop3ByOrderByIdAsc();

	Book save(Book book);

	boolean removeOne(Long id);

	Page<Book> findByPage(Pageable pageable);

	Page<Book> findByCategoryWithPage(String category, Pageable pageable);

}
