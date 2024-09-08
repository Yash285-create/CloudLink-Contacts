package com.yash.scm.forms;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Component
public class ContactForm {
	
	
	@Size(min=3,max=20,message="Name must be of 3 characters")
	private String name;
	
	@NotBlank(message="Invalid Email Address")
	@Pattern(regexp = "^[a-zA-Z0-9.\\-\\/+=@_ ]*$" , message="invalid email")
	@Email
	private String email;
	
	//@Size(min=10,max=10)
	@Pattern(regexp="^[0-9]{10}$",message="must be of 10 digits")
	private String phoneNumber;
	
	private String address;
	
	private String description;
	
	private MultipartFile picture;
	
	private String image;
	
	private boolean favorite=false;
	
	private String websiteLink;
	
	private String linkedInLink;

}
