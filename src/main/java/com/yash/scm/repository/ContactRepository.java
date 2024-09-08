package com.yash.scm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yash.scm.entities.Contact;
import com.yash.scm.entities.User;

public interface ContactRepository extends JpaRepository<Contact,String> {
	
          // find the contact by user
	
	// custom finder method
	Page<Contact> findByUser(User user,Pageable pageable);
	
	//custom query method
	@Query("select c from Contact c where c.user.id = :userId")
	List<Contact> findByUserId(@Param("userId")String userId);
	
	Page<Contact> findByUserAndNameContaining(User user,String name,Pageable pageable);
	Page<Contact> findByUserAndEmailContaining(User user,String email,Pageable pageable);
	Page<Contact> findByUserAndPhoneNumberContaining(User user,String phone,Pageable pageable);
	
	@Query("SELECT COUNT(c.email) FROM Contact c WHERE c.user.id = :userId")
    long countEmailsByUserId(@Param("userId") String userId);
	
	@Query("SELECT COUNT(c.favorite) FROM Contact c WHERE c.user.id = :userId")
    long countFavoritesByUserId(@Param("userId") String userId);
}
