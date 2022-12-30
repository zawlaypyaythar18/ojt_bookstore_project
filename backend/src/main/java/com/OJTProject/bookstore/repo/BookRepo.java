package com.OJTProject.bookstore.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.Book;

public interface BookRepo extends JpaRepository<Book, Long> {

	List<Book> findAll();

	List<Book> findByCategory(String category);

	List<Book> findByTitleContaining(String title);

	List<Book> findTop3ByOrderByIdAsc();

	Page<Book> findAllByOrderByTitleAsc(Pageable pageable);

	Page<Book> findByCategory(String category, Pageable pageable);

}
