package com.yash.scm.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.yash.scm.helper.AppConstant;
import com.yash.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	// constructor injection dont need to write @Autowired
	private Cloudinary cloudinary;
	
	public ImageServiceImpl(Cloudinary cloudinary)
	{
		this.cloudinary=cloudinary;
	}
	
	
	public String uploadImage(MultipartFile image,String filename)
	{
		
		 try {
	            byte[] data = new byte[image.getInputStream().available()];
	            image.getInputStream().read(data);
	            cloudinary.uploader().upload(data, ObjectUtils.asMap(
	                    "public_id", filename));

	            return this.getUrlFromPublicId(filename);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }

		
	}


	@Override
	public String getUrlFromPublicId(String publicId) {
		
		return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstant.CONTACT_IMAGE_WIDTH)
                                .height(AppConstant.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstant.CONTACT_IMAGE_CROP))
                .generate(publicId);
		
	}
	 
}
