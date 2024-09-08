package com.yash.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.scm.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private EmailService email;

	@Test
	void SendEmailTest()
	{
		
		email.sendEmail("ys.yashmuley@gmail.com","test mail for scm" ,"Welcome to CloudLink-Contacts");
		
	}
	
}
