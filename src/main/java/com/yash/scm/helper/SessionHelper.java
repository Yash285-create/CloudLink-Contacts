package com.yash.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


// ye class session ko remove karegi successful registration ke baad mai 
// ye nahi kra to wo msg hatega hi nahi successful registration ka


@Component
public class SessionHelper {
	
	
	public static void removeMessage()
	{
//		try 
//		{
//			HttpSession session= (HttpSession) RequestContextHolder.getRequestAttributes();
//		     session.removeAttribute("message");
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		
		 try {
	            // Obtain the HttpServletRequest from the RequestContextHolder
	            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	            if (attributes != null) {
	                HttpServletRequest request = attributes.getRequest();
	                HttpSession session = request.getSession(false); // Get existing session or return null

	                if (session != null) {
	                    session.removeAttribute("message");
	                } else {
	                    System.out.println("No session found.");
	                }
	            } else {
	                System.out.println("No RequestAttributes found.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		
		
		
		
		
	}

}
