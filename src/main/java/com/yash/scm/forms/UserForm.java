package com.yash.scm.forms;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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
public class UserForm {
	
	@NotBlank(message="Name is required")
	@Size(min=3,max=20,message="must be of 3 characters")
	private String name;
	
	@NotBlank(message="Invalid Email Address")
	@Pattern(regexp = "^[a-zA-Z0-9.\\-\\/+=@_ ]*$" , message="invalid email")
	@Email
	private String email;
	
	@NotBlank(message = "Invalid Password minimum 4 characters are required")
	@Size(min=4,max=20)
	private String password;
	
	
	@Pattern(regexp="^[0-9]{10}$",message="must be of 10 digits")
	private String phoneNumber;
	
	private String about;
	

	
	
	
	
	
}
