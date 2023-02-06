package com.ecom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.User;
import com.ecom.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository crepo;
	
	public User findByUserId(int userid) {
		Optional<User> cust=crepo.findById(userid);
		if(cust.isPresent())
			return cust.get();
		else
			return null;
	}
}
