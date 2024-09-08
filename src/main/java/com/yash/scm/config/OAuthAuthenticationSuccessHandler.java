package com.yash.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.yash.scm.entities.Provider;
import com.yash.scm.entities.User;
import com.yash.scm.helper.AppConstant;
import com.yash.scm.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepo;
 
	// handles login with google and github
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
       
		// yaha check kr rahe hai ki ye google/github kisse login kiya hai
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

		String id = token.getAuthorizedClientRegistrationId();

		// iski help se hum name email profile pic nikal sakte hai 
		DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

		User u = new User();
		u.setUserId(UUID.randomUUID().toString());
		u.setRoles(List.of(AppConstant.ROLE_USER));
		u.setPassword("Default Password");
		u.setEmailVerified(true);

		if (id.equalsIgnoreCase("google")) {

			String email = user.getAttribute("email").toString();
			String name = user.getAttribute("name").toString();
			String picture = user.getAttribute("picture").toString();
			String providerId = user.getName();

			// creating user and saving in database here ye jo user google se login karega
			// wo
			// yaha password dummy diya hai kyoki Google se manage hoga
			u.setName(name);
			u.setEmail(email);
			u.setAbout("Login with Google");
			u.setProfilePic(picture);
			u.setProvider(Provider.GOOGLE);
			u.setProviderId(providerId);

		} else if (id.equalsIgnoreCase("github")) {

			String email = user.getAttribute("name") != null ? user.getAttribute("name").toString()
					: user.getAttribute("login").toString() + "@gmail.com";
			String picture = user.getAttribute("avatar_url").toString();
			String name = user.getAttribute("login").toString();
			String providerId = user.getName();

			u.setName(name);
			u.setEmail(email);
			u.setAbout("Login with Github");
			u.setProfilePic(picture);
			u.setProvider(Provider.GITHUB);
			u.setProviderId(providerId);

		}

		// saving google user in the database
		Optional<User> opt = userRepo.findByEmail(u.getEmail());
		if (opt.isEmpty())
			userRepo.save(u);

		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

	}

}
