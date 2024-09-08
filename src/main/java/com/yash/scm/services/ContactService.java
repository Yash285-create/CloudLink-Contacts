package com.yash.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yash.scm.entities.Contact;
import com.yash.scm.entities.User;

public interface ContactService {

	Contact addContact(Contact contact);
	Contact updateContact(Contact contact);
	void    deleteContact(Contact contact);
	List<Contact> showAllContacts();
	Contact ShowContactById(String id);
	List<Contact> search(String name,String email,String phoneNumber);
	List<Contact> getByUserId(String userId);
	Page<Contact> findByUser(User user,int page,int size,String sortBy,String direction);
	Page<Contact> findByName(User user,String userName,int page,int size,String sortBy,String direction);
	Page<Contact> findByEmail(User user,String email,int page ,int size,String sortBy,String direction);
	Page<Contact> findByPhoneNumber(User user,String phone,int page ,int size,String sortBy,String direction);
	void delete(String id);
	
    
}
