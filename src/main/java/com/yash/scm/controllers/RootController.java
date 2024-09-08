package com.yash.scm.controllers;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yash.scm.entities.User;
import com.yash.scm.helper.Helper;
import com.yash.scm.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// All the methods will get executed by @ControlAdvice for all the requests
@ControllerAdvice
public class RootController {

	@Autowired
	private UserService userService;
	
	// Ye method sare endpoints pai data pahucha degi 
		//phele keval /profile pai hi ja raha tha ya /dashboard pai
		@ModelAttribute
		public void LoggedInUser(Model model,Authentication authentication)
		{
			if(authentication==null) return;
			
			// fetching email id of the loggedin user
			String emailId=Helper.getUserByEmailId(authentication);
			log.info("Email Id {}",emailId);
			
			//fetching user data based on the email id
			User user=userService.findByEmail(emailId);
			 
			    model.addAttribute("loggedInUser",user);	 
				 
			 
			 
		}
	
}
