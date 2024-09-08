package com.yash.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	public String uploadImage(MultipartFile file,String fileName);
	String getUrlFromPublicId(String publicId);
}
