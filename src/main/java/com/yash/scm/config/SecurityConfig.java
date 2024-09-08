package com.yash.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.yash.scm.helper.Message;
import com.yash.scm.helper.MessageType;
import com.yash.scm.services.impl.SecurityCustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {

	@Autowired
	SecurityCustomUserDetailsService securityCustomUserDetailsService;
	
	@Autowired
	OAuthAuthenticationSuccessHandler oAuthSuccessHandler;
	
	// configuration of authentication
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
      DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
	  daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailsService);
	  daoAuthenticationProvider.setPasswordEncoder(password());
      
      return daoAuthenticationProvider;
	}
	
	@Bean
	public PasswordEncoder password()
	{
		return new BCryptPasswordEncoder();
	}
	
	// url configuration
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.authorizeHttpRequests(authorize->{
			
			//authorize.requestMatchers("/home","/register","/services").permitAll();
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
			
			
		});
		
		//httpSecurity.formLogin(Customizer.withDefaults()); // default login page spring security ka dikhega
		
		httpSecurity.formLogin(formLogin ->{
			
			
			 formLogin.loginPage("/login");
	         formLogin.loginProcessingUrl("/authenticate");
	         formLogin.defaultSuccessUrl("/user/profile");
//	         formLogin.failureForwardUrl("/login?error=true");	            
	         formLogin.usernameParameter("email");
	         formLogin.passwordParameter("password");
	         
	         
	         
	         formLogin.failureHandler(new AuthenticationFailureHandler() {
				
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					
					if(exception instanceof DisabledException)
					{
						
						Message msg=Message.builder().content("Verification Link sent to your Email Id, Please verify it to activate your account !!").type(MessageType.yellow).build();
						HttpSession session=request.getSession();
						session.setAttribute("message", msg);
						response.sendRedirect("/login");
						
					}
				
					else if(exception instanceof BadCredentialsException)
					{
						Message msg=Message.builder().content("Bad Credentials !!").type(MessageType.red).build();
						HttpSession session=request.getSession();
						session.setAttribute("message", msg);
						response.sendRedirect("/login");
					}
					
				}
			});
	         
	         
			

		});
		httpSecurity.csrf(AbstractHttpConfigurer::disable); // isse logout chalega
		httpSecurity.logout(logoutForm ->{
			
			logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
		
     	});
		
		//httpSecurity.oauth2Login(Customizer.withDefaults());
		httpSecurity.oauth2Login(oauth->{
			
			oauth.loginPage("/login");
		    oauth.successHandler(oAuthSuccessHandler);
		  
		
		});
		
		
		
		
		
		return httpSecurity.build();
	}
	
	     
}
