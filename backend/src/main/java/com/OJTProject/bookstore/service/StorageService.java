package com.OJTProject.bookstore.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	public String saveBookImage(MultipartFile file, String fileType);

	public byte[] load(String filePath);

	public boolean deleteBookImage(String filePath);

	public String updateBookImage(MultipartFile file, String fileType, String filePath);
	
	public void clearAll();

}
