package com.yash.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yash.scm.entities.User;
import com.yash.scm.helper.Helper;
import com.yash.scm.repository.ContactRepository;
import com.yash.scm.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	 ContactRepository contactRepo;
	
	@GetMapping("/dashboard")
	public String userDashboard(Authentication auth,Model model)
	{
		
		String email = Helper.getUserByEmailId(auth);

		User user = userService.findByEmail(email);

		String userId=user.getUserId();
		
		
		long count=contactRepo.countEmailsByUserId(userId);

		model.addAttribute("count",count);
		
		long favorites=contactRepo.countFavoritesByUserId(userId);
		model.addAttribute("favorite",favorites);
		
		model.addAttribute("contacts",ContactController.queueContact);
		return "user/dashboard";
	}
	
	
	@GetMapping("/profile")
	public String userProfile()
	{
		return "user/profile";
	}
}
