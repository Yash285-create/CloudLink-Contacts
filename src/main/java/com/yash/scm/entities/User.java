package com.yash.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User implements UserDetails {
	
	@Id
	private String userId;
	@Column(nullable=false)
	private String name;
	@Column(nullable = false,unique=true)
	private String email;
	private String password;
	@Column(length = 1000)
	private String about;
	@Column(length = 5000)
	private String profilePic;
	private String phoneNumber;
	
	@Builder.Default
	private boolean enabled=false;
	private boolean emailVerified=false;
	private boolean mobileVerified=false;
	
	private String providerId;
	@Enumerated(value = EnumType.STRING)  // by default value humne yaha pai set ki lekin wo le nahi raha tha to isiliye ye likhna padega 
	
	@Builder.Default
	private Provider provider=Provider.SELF;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval = true)
	private List<Contact> contacts=new ArrayList<>();
	
	private String emailToken;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private List<String> roles=new ArrayList();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	Collection<SimpleGrantedAuthority> grantedAuthority=roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return grantedAuthority;
	}
	
	@Override
	public String getUsername() {
            return  this.email;

	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
