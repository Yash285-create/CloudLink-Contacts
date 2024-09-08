package com.yash.scm.controllers;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yash.scm.entities.Contact;
import com.yash.scm.entities.User;
import com.yash.scm.forms.ContactForm;
import com.yash.scm.forms.ContactSearchForm;
import com.yash.scm.helper.AppConstant;
import com.yash.scm.helper.Helper;
import com.yash.scm.helper.Message;
import com.yash.scm.helper.MessageType;
import com.yash.scm.repository.ContactRepository;
import com.yash.scm.services.ContactService;
import com.yash.scm.services.UserService;
import com.yash.scm.services.impl.ImageServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("user/contacts")
public class ContactController {

	@Autowired
	private ImageServiceImpl imageService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private UserService userService;
	
	@Autowired
	 ContactRepository contactRepo;

   public static Queue<ContactForm> queueContact=new LinkedList<>();
	
	@GetMapping("/add")
	public String ViewAddContact(Model model) {

		ContactForm contactForm = new ContactForm();
		contactForm.setFavorite(true);
		
		
	

		model.addAttribute("contactForm", contactForm);

		return "user/add_contact";
	}

	// user bhi set krna hai isiliye humne authenticaion liya hai
	@PostMapping("/add")
	public String doContactProcessor(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
			Authentication auth, HttpSession session) {
		if (result.hasErrors()) {

			Message msg = Message.builder().content("Please Correct The Following Errors !!").type(MessageType.red)
					.build();
			session.setAttribute("message", msg);
			return "user/add_contact";
		}

		String userName = Helper.getUserByEmailId(auth);

		User user = userService.findByEmail(userName);

//		  Contact con=Contact.builder()
//					.name(contactForm.getName())
//					.email(contactForm.getEmail())
//					.phoneNumber(contactForm.getPhoneNumber())
//					.address(contactForm.getAddress())
//					.description(contactForm.getDescription())
//					.favorite(contactForm.isFavorite())
//					.linkedInLink(contactForm.getLinkedInLink())
//					.user(user)
//					.websiteLink(contactForm.getWebsiteLink());
//		  

		
		
		
		if(queueContact.size()==2)
		{
			queueContact.poll();
		}
		
		queueContact.add(contactForm);
		
		System.out.println(queueContact.size());
		System.out.println(queueContact);
		
		Contact con = new Contact();
		con.setName(contactForm.getName());
		con.setEmail(contactForm.getEmail());
		con.setPhoneNumber(contactForm.getPhoneNumber());
		con.setAddress(contactForm.getAddress());
		con.setDescription(contactForm.getDescription());
		con.setFavorite(contactForm.isFavorite());
		con.setLinkedInLink(contactForm.getLinkedInLink());
		con.setUser(user);
		con.setWebsiteLink(contactForm.getWebsiteLink());

		// image process
		// code to upload the file on cloud
		log.info("File Name is {}", contactForm.getPicture().getOriginalFilename());

		if (!contactForm.getPicture().isEmpty()) {
			String fileName = UUID.randomUUID().toString();
			String fileUrl = imageService.uploadImage(contactForm.getPicture(), fileName);
			con.setPicture(fileUrl);
			con.setCloudinaryImagePublicId(fileName);

		}

		Contact contact = contactService.addContact(con);

		// setting the message through session
		Message msg = Message.builder().content("Contact Created Successfully").type(MessageType.green).build();
		session.setAttribute("message", msg);

		return "redirect:/user/contacts/add";
	}

	@GetMapping
	public String viewContactPage(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
			Authentication auth) {
		String email = Helper.getUserByEmailId(auth);

		User user = userService.findByEmail(email);

		Page<Contact> pageContact = contactService.findByUser(user, page, size, sortBy, direction);

		model.addAttribute("pageContact", pageContact);
		model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

		return "/user/view";
	}

	@GetMapping("/search")
	public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Authentication auth)
			throws ParseException {

		Page<Contact> con = null;
		String keyword = contactSearchForm.getKeyword();

		User user = userService.findByEmail(Helper.getUserByEmailId(auth));

		if (contactSearchForm.getField().equalsIgnoreCase("name")) {
			con = contactService.findByName(user, keyword, page, size, sortBy, direction);
		}

		if (contactSearchForm.getField().equalsIgnoreCase("email")) {
			con = contactService.findByEmail(user, keyword, page, size, sortBy, direction);
		}

		if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {

			con = contactService.findByPhoneNumber(user, keyword, page, size, sortBy, direction);
		}

		model.addAttribute("pageContact", con);
		model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
		model.addAttribute("contactSearchForm", contactSearchForm);

		return "user/search";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id, HttpSession session) {
		Message msg = Message.builder().content("Contact Deleted Successfully !!").type(MessageType.green).build();
		session.setAttribute("message", msg);
		contactService.delete(id);
		return "redirect:/user/contacts";
	}

	@GetMapping("/view/{id}")
	public String updateContact(@PathVariable("id") String id, HttpSession session, Model model) {
		Message msg = Message.builder().content("Contact Deleted Successfully !!").type(MessageType.green).build();
		// session.setAttribute("message",msg);

		Contact contact = contactService.ShowContactById(id);
		ContactForm contactForm = new ContactForm();

		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.isFavorite());
		contactForm.setLinkedInLink(contact.getLinkedInLink());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setImage(contact.getPicture());

		model.addAttribute("contactForm", contactForm);
		model.addAttribute("id", id);

		return "/user/update_contact_view";
	}

	@PostMapping("/update/{id}")
	public String doUpdateContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
			@PathVariable("id") String id, HttpSession session, Model model) {

		if (result.hasErrors()) {

			Message msg = Message.builder().content("Please Correct The Following Errors !!").type(MessageType.red)
					.build();
			session.setAttribute("message", msg);

			return "user/update_contact_view";
		}

		Contact contact = contactService.ShowContactById(id);

		contact.setId(id);
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setFavorite(contactForm.isFavorite());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setWebsiteLink(contactForm.getWebsiteLink());

		// process image
		if (contactForm.getPicture() != null && !contactForm.getPicture().isEmpty()) {
			String fileName = UUID.randomUUID().toString();
			String imageUrl = imageService.uploadImage(contactForm.getPicture(), fileName);
			contactForm.setImage(imageUrl);
			contact.setCloudinaryImagePublicId(fileName);
			contact.setPicture(imageUrl);

		}

		Contact UpdatedContact = contactService.updateContact(contact);

		Message msg = Message.builder().content("Contact Updated Successfully !!").type(MessageType.green).build();
		session.setAttribute("message", msg);

		return "/user/update_contact_view";
	}

	
	
	

	
	
}
