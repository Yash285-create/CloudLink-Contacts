package com.yash.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yash.scm.entities.User;
import com.yash.scm.helper.Message;
import com.yash.scm.helper.MessageType;
import com.yash.scm.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam("token")String token,HttpSession session)
	{
		User user=userRepo.findByEmailToken(token).orElse(null);
		
		
		if(user!=null && token.equalsIgnoreCase(user.getEmailToken()) )
		{
			System.out.println(user.getName());
			user.setEmailVerified(true);
			user.setEnabled(true);
			
			userRepo.save(user);
			
			Message message=new Message();
			message.setContent("Email Verified Successfully , Please login to continue");
			message.setType(MessageType.blue);
			
			session.setAttribute("message",message);
			
			
			return "/login";
		}
		
		
		Message message=new Message();
		message.setContent("Email not verified , Something went wrong");
		message.setType(MessageType.red);
		
		session.setAttribute("message",message);
		
		return "redirect:/register";
	}
	
}
