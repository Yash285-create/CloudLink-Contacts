 package com.yash.scm.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.yash.scm.entities.User;
import com.yash.scm.forms.UserForm;
import com.yash.scm.helper.Message;
import com.yash.scm.helper.MessageType;
import com.yash.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
	
	@Autowired
	private UserForm userForm;
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String slash(Model model)
	{
				
		return "/home";
	}
	
	
	@GetMapping("/home")
	public String view(Model model)
	{
		model.addAttribute("name","Yash Muley");
		model.addAttribute("company","Wipro");
		model.addAttribute("city","Pune");
		
		return "home";
	}
	
	
	@GetMapping("/about")
	public String aboutPage(Model model)
	{
	  model.addAttribute("isLogin",true);
	  return "about";
	}
	
	@GetMapping("/service")
	public String servicePage(Model model)
	{
	
		return "service";
	}
	
	
	@GetMapping("/contact")
	public String contact(Model model)
	{
		model.addAttribute("name","Yash Muley");
		model.addAttribute("company","Wipro");
		model.addAttribute("city","Pune");
		
		return "contact";
	}
	
	@GetMapping("/login")
	public String login(Model model)
	{
		
		return "login";
	}
	
//	@PostMapping("/authenticate")
//	public String dologin(Model model)
//	{
//		System.out.println("inside dologin method");
//		return "redirect:/home";
//	}
	
	
	@GetMapping("/register")
	public String signUp(Model model)
	{
	
		System.out.println("inside register method");
		//sending an empty object to pickup the data
		//we can also send default values
	    model.addAttribute("userForm", userForm);	
		return "register";
	}
	
	@PostMapping("/do-register")
	public String doRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult , HttpSession session,Model model)
	{
		System.out.println(userForm);
		
		if(rBindingResult.hasErrors())
		{
			return "register";
		}
			

		//Builder pattern yaha use nahi kr sakte hai ye remaining values ko null de raha hai like providers 
		//humne SELF liya lekin wo false kyoki builder by default values initialize nahi krta
		// so we have two options 1) ki field ke uppar @enumerated and @builder.default use krna hoga
		// 2) user new user krke kam karo with  @enumerated
		User user=User.builder().name(userForm.getName()).email(userForm.getEmail())
		.password(userForm.getPassword())
		.phoneNumber(userForm.getPhoneNumber())
		.about(userForm.getAbout())
		.profilePic("/images/Default-Profile-Pic.png")
		.build();
		
		// checking whether the email exists or not
		
		User savedUser=userService.findByEmail(user.getEmail());
        
		
		if(savedUser!=null)
		{
			
			
			Message msg=Message.builder()
					.content("Email already exists !!")
					.type(MessageType.red)
					.build();
					session.setAttribute("message",msg);
					
					model.addAttribute("userForm",userForm);
			return "register";
		}
		
		 savedUser=userService.createUser(user);
		
		//setting the message through session
		Message msg=Message.builder()
		.content("Please verify your email id , we have sent you a verification link on your email")
		.type(MessageType.blue)
		.build();
		session.setAttribute("message",msg);
		
		return "redirect:/register";
	}
	
	
	
}
