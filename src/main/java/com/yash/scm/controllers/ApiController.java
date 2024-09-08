package com.yash.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.scm.entities.Contact;
import com.yash.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ContactService contactService;
	@GetMapping("/contacts/{id}")
	public Contact getContact(@PathVariable("id") String id)
	{
		return contactService.ShowContactById(id);
	}
}
