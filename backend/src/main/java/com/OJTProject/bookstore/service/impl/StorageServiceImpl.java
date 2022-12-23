package com.OJTProject.bookstore.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.OJTProject.bookstore.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
	
	private final Path storagePath;
	
	private final Random random = new Random();
	
	@Value("${custom.delete.images}")
	private boolean customDeleteImages;
	
	@Autowired
	public StorageServiceImpl() throws IOException {
		
		Path storagePath = Paths.get("").resolve("src").resolve("main")
				.resolve("resources").resolve("static").resolve("images").resolve("books");
		if (!Files.exists(storagePath)) {
			Files.createDirectories(storagePath);
		}
		this.storagePath = storagePath;
		
	}

	@Override
	public String saveBookImage(MultipartFile file, String fileType) {
		
		String filePath = null;

		try {
			String fileName = random.nextInt(999999) + "_"
					+ StringUtils.cleanPath(file.getOriginalFilename());
			Files.copy(
					file.getInputStream(), this.storagePath.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING
			);
			switch (fileType) {
				case "image/jpg" :
				case "image/jpeg" :
					filePath = "/api/admin/book/media/jpg/" + fileName;
					break;
				case "image/png" :
				default :
					filePath = "/api/admin/book/media/png/" + fileName;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePath;
	}

	@Override
	public byte[] load(String filePath) {
		
		byte[] retBytes = null;

		try {
			Path file = this.storagePath.resolve(filePath);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				retBytes = StreamUtils
						.copyToByteArray(resource.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retBytes;
		
	}

	@Override
	public boolean deleteBookImage(String filePath) {
		
		String fileName = filePath.substring(11);
//		System.out.println(filePath.substring(11));

		try {
			Files.delete(this.storagePath.resolve(fileName));
//			FileUtils.forceDelete(this.storagePath.resolve(fileName).toFile());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public String updateBookImage(MultipartFile file, String fileType, String filePath) {
		
		String retfilePath = null;

		filePath = filePath.replace("/api/admin/book/media/jpg/", "");
		filePath = filePath.replace("/api/admin/book/media/png/", "");
		try {
			if (filePath != null && filePath != "") {
				try {
					Files.delete(this.storagePath.resolve(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String fileName = random.nextInt(999999) + "_"
					+ StringUtils.cleanPath(file.getOriginalFilename());
			Files.copy(
					file.getInputStream(), this.storagePath.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING
			);
			switch (fileType) {
				case "image/jpg" :
				case "image/jpeg" :
					retfilePath = "/api/admin/book/media/jpg/" + fileName;
					break;
				case "image/png" :
				default :
					retfilePath = "/api/admin/book/media/png/" + fileName;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retfilePath;
		
	}

	@Override
	public void clearAll() {
		
		try {
			if (customDeleteImages) {
				Files.walk(storagePath).sorted().forEach(t -> {
					try {
						if (!Files.isDirectory(t)) {
							Files.deleteIfExists(t);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
