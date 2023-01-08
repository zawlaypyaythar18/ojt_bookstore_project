package com.OJTProject.bookstore.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Required")
	private String title;
	
	@Column(nullable = false)
	private String author;
	
	
	@Column(nullable = false)
	private String publisher;
	
	@Column(nullable = false)
	private String publicationDate;
	
	@Column(nullable = false)
	private String language;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private String numberOfPages;
	
	@Column(nullable = false)
	private String format;
	
	@Column(nullable = false)
	private Long isbn;
	
	@Column(nullable = false)
	private Double shippingWeight;
	
	@Column(nullable = false)
	private Double listPrice;
	
	@Column(nullable = false)
	private Double ourPrice;
	
	private Boolean active = true;

	@Column(columnDefinition = "text")
	private String description;

	private Integer inStockNumber;

	@Column(length = 200)
	@NotBlank(message = "Required")
	private String posterPath;

	@OneToMany(mappedBy = "book")
	@JsonIgnore
	private List<UserCartItemBook> userCartItemBookList;

	public Book() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getInStockNumber() {
		return inStockNumber;
	}

	public void setInStockNumber(Integer inStockNumber) {
		this.inStockNumber = inStockNumber;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public List<UserCartItemBook> getUserCartItemBookList() {
		return userCartItemBookList;
	}

	public void setUserCartItemBookList(List<UserCartItemBook> userCartItemBookList) {
		this.userCartItemBookList = userCartItemBookList;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", publicationDate=" + publicationDate + ", language=" + language + ", category=" + category
				+ ", numberOfPages=" + numberOfPages + ", format=" + format + ", isbn=" + isbn + ", shippingWeight="
				+ shippingWeight + ", listPrice=" + listPrice + ", ourPrice=" + ourPrice + ", active=" + active
				+ ", description=" + description + ", inStockNumber=" + inStockNumber + ", posterPath=" + posterPath
				+ "]";
	}

}
