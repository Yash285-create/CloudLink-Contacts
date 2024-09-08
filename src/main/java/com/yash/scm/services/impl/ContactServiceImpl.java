package com.yash.scm.services.impl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yash.scm.entities.Contact;
import com.yash.scm.entities.User;
import com.yash.scm.exceptions.ResourceNotFoundException;
import com.yash.scm.repository.ContactRepository;
import com.yash.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepo;
	
	@Override
	public Contact addContact(Contact contact) {
		
		contact.setId(UUID.randomUUID().toString());
		
		return contactRepo.save(contact);
		
	}

	@Override
	public Contact updateContact(Contact contact) {
		
		Contact con=contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact Does Not Exists"));
		
		con.setName(contact.getName());
		con.setEmail(contact.getEmail());
		con.setPhoneNumber(contact.getPhoneNumber());
		con.setAddress(contact.getAddress());
		con.setDescription(contact.getDescription());
		con.setFavorite(contact.isFavorite());
		con.setLinkedInLink(contact.getLinkedInLink());
		con.setWebsiteLink(contact.getWebsiteLink());
		con.setPicture(contact.getPicture());
		con.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
		
		return contactRepo.save(con);
		
		
	}

	@Override
	public void deleteContact(Contact contact) {
		
	  if(contactRepo.findById(contact.getId()) != null)
	  {
		  contactRepo.delete(contact);
	  }
	  else
	  {
		  throw new ResourceNotFoundException("Contact Not Found ");
	  }
	}

	@Override
	public void delete(String id) {
		
	
	  
		 contactRepo.deleteById(id);
	  
	  
	}
	@Override
	public List<Contact> showAllContacts() {
		
		return contactRepo.findAll();
		
	}

	@Override
	public Contact ShowContactById(String id) {
		
		return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact Not Found "+id));
		
		
	}

	@Override
	public List<Contact> search(String name, String email, String phoneNumber) {
		
		return null;
	}

	@Override
	public List<Contact> getByUserId(String userId) {
		
		 return contactRepo.findByUserId(userId);
		
		
	}

	

	@Override
	public Page<Contact> findByUser(User user,int page ,int size,String sortBy,String direction) {
		
		Sort sort=direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		PageRequest pageable=PageRequest.of(page, size,sort);
	
		
		return contactRepo.findByUser(user,pageable);
		
	}
	
	public  Page<Contact> findByName(User user,String userName,int page ,int size,String sortBy,String direction)
	{
Sort sort=direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		PageRequest pageable=PageRequest.of(page, size,sort);
		
		return contactRepo.findByUserAndNameContaining(user,userName,pageable);
		
	}
	
	public  Page<Contact> findByEmail(User user,String email,int page ,int size,String sortBy,String direction)
	{
Sort sort=direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		PageRequest pageable=PageRequest.of(page, size,sort);
		
		return contactRepo.findByUserAndEmailContaining(user,email,pageable);
		
	}
	
	public  Page<Contact> findByPhoneNumber(User user,String phone,int page ,int size,String sortBy,String direction)
	{
Sort sort=direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		PageRequest pageable=PageRequest.of(page, size,sort);
		
		return contactRepo.findByUserAndPhoneNumberContaining(user,phone,pageable);
		
	}
	
	
}
