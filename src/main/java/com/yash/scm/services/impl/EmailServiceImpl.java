package com.yash.scm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yash.scm.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.properties.domain}")
	private  String from;
	
	SimpleMailMessage message=new SimpleMailMessage();
	
	@Override
	public void sendEmail(String to, String subject, String body) {
		
		  message.setTo(to);
		  message.setSubject(subject);
		  message.setText(body);
		  message.setFrom(from);
		  
		  javaMailSender.send(message);
		
	}

	@Override
	public void sendEmailWithHtml() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmailWithAttachment() {
		// TODO Auto-generated method stub
		
	}

}
