package com.OJTProject.bookstore.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

@Entity

public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column (length=30, nullable = false)
	private String title;
	
	@Column (nullable = false)
	private String author;

	@Column (columnDefinition = "Enum('Fiction', 'Horror', 'Romance', 'Programming', 'Engineering')")
	@Enumerated(EnumType.STRING)
	private BookCategory category;
	
	@Column (nullable = false)
	private String publisher;
	
	@Column (nullable = false)
	private LocalDate publicationDate;
	
	@Column (nullable = false)
	private String language;
	
	@Column (nullable = false)
	private String numberOfPages;
	
	@Column (nullable = false)
	private String format;
	
	@Column (nullable = false)
	private Long isbn;
	
	@Column (nullable = false)
	private Double shippingWeight;
	
	@Column (nullable = false)
	private Double listPrice;
	
	@Column (nullable = false)
	private Double ourPrice;
	
	@Column (nullable = false)
	private Boolean active = true;
	
	@Column (nullable = false)
	private Integer inStockNumber;
	
	@Column(columnDefinition = "text")
	private String description;

	@Column(length = 200)
	@NotBlank(message = "Required")
	private String posterPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BookCategory getCategory() {
		return category;
	}

	public void setCategory(BookCategory category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	
	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(String numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public Double getShippingWeight() {
		return shippingWeight;
	}

	public void setShippingWeight(Double shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public Double getOurPrice() {
		return ourPrice;
	}

	public void setOurPrice(Double ourPrice) {
		this.ourPrice = ourPrice;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getInStockNumber() {
		return inStockNumber;
	}

	public void setInStockNumber(Integer inStockNumber) {
		this.inStockNumber = inStockNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public Book(int id, String title, String author, BookCategory category, String publisher, LocalDate publicationDate,
			String language, String numberOfPages, String format, Long isbn, Double shippingWeight, Double listPrice,
			Double ourPrice, Boolean active, Integer inStockNumber, String description,
			@NotBlank(message = "Required") String posterPath) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.language = language;
		this.numberOfPages = numberOfPages;
		this.format = format;
		this.isbn = isbn;
		this.shippingWeight = shippingWeight;
		this.listPrice = listPrice;
		this.ourPrice = ourPrice;
		this.active = active;
		this.inStockNumber = inStockNumber;
		this.description = description;
		this.posterPath = posterPath;
	}

	public Book() {
		
	}

}
