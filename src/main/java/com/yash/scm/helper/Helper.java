package com.yash.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {
	
	public static String getUserByEmailId(Authentication authentication)
	{
		if(authentication instanceof OAuth2AuthenticationToken)
		{
			 DefaultOAuth2User user  =(DefaultOAuth2User)authentication.getPrincipal();
			 
			 OAuth2AuthenticationToken token  =(OAuth2AuthenticationToken)authentication;
			 
			 String id=token.getAuthorizedClientRegistrationId();
			 
			 String email="";
			 
			 if(id.equalsIgnoreCase("google"))
			 {
				 email=user.getAttribute("email").toString();
			     
			 }
			 else if(id.equalsIgnoreCase("github"))
			 {
				 email=user.getAttribute("name") !=null ? user.getAttribute("name").toString() : user.getAttribute("login").toString() + "@gmail.com";
			     
			 }
			return email;
		}
		else
		{
			return authentication.getName();
		}
	}

	public static String getEmailVerificationToken(String token) {
		
		String link="http://localhost:8080/auth/verify-email?token=" + token;
		
		return link;
	}

}
