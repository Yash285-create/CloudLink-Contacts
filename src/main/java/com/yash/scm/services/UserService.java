package com.yash.scm.services;

import java.util.List;
import java.util.Optional;

import com.yash.scm.entities.User;

public interface UserService {
	
	User createUser(User user);
	Optional<User> getUserBId(String id);
	Optional<User> updateUser(User user);
	void deleteUser(String id);
	boolean isUserExist(String userId);
	Optional<User> isUserExistByEmailId(String email);
	List<User> getAllUsers();
	User findByEmail(String email);

}
