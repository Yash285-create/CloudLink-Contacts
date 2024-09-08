package com.yash.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yash.scm.entities.User;
import com.yash.scm.exceptions.ResourceNotFoundException;
import com.yash.scm.helper.AppConstant;
import com.yash.scm.helper.Helper;
import com.yash.scm.repository.UserRepository;
import com.yash.scm.services.EmailService;
import com.yash.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncode;
	
	@Autowired
	private EmailService emailService;
	
	public User createUser(User user)
	{
	  user.setUserId(UUID.randomUUID().toString());
	  
	  user.setPassword(passwordEncode.encode(user.getPassword()));
	  user.setRoles(List.of(AppConstant.ROLE_USER));
	  
	  String email=user.getEmail();
	  
	  String token=UUID.randomUUID().toString();
	  
	  user.setEmailToken(token);
	  
	  String verifyLink=Helper.getEmailVerificationToken(token);
	  
	 
	  
	  emailService.sendEmail(email,"Verification Link for Account Activation","Click on the link below \n "+verifyLink);
	  
	  
	  
	  User savedUser= userRepo.save(user);
	  
	  
	  
	  return savedUser;
	}

	@Override
	public Optional<User> getUserBId(String id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {
		
		User user2=userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists"));
		
		
				   user2.setName(user.getName());
				   user2.setEmail(user.getEmail());
				   user2.setPassword(user.getPassword());
				   user2.setAbout(user.getAbout());
				   user2.setProfilePic(user.getProfilePic());
				   user2.setPhoneNumber(user.getPhoneNumber());
				   user2.setEnabled(user.isEnabled());
				   user2.setEmailVerified(user.isEmailVerified());
				   user2.setMobileVerified(user.isMobileVerified());
				   user2.setProviderId(user.getProviderId());
				   user2.setProvider(user.getProvider());
		
		
		User updatedUser=userRepo.save(user2);
		 return Optional.ofNullable(updatedUser); // data hoga to data chalajayega nahi to empty optional chala jayega
		
		
	}

	@Override
	public void deleteUser(String id) {
		User u=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID Does Not Exists"));
		     userRepo.delete(u);
	}

	@Override
	public boolean isUserExist(String userId) {
		Optional<User>u=userRepo.findById(userId);
		if(!u.isEmpty()) return true; 
		return false;
	}

	@Override
	public Optional<User> isUserExistByEmailId(String email) {
	 User u=userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException(email+" Does Not Exists"));
       return Optional.ofNullable(u);
	}

	@Override
	public List<User> getAllUsers() {
	
		return userRepo.findAll();
		
	}

	@Override
	public User findByEmail(String email) {
		User user=userRepo.findByEmail(email).orElse(null);
		return  user;
	}

}
