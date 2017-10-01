package com.jfeesoft.officeRest.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jfeesoft.officeRest.model.Permission;
import com.jfeesoft.officeRest.model.Role;
import com.jfeesoft.officeRest.model.SystemUser;
import com.jfeesoft.officeRest.repository.SystemUserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private SystemUserRepository systemUserRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		SystemUser systemUser = systemUserRepository.findByEmailAddress(userName);

		User userdetails = new User(systemUser.getSurname(), systemUser.getPassword(), systemUser.getIsActive(),
				accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(systemUser.getRoles()));

		return userdetails;
	}

	@Bean
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(getBcryptPasswordEncoder());
		authProvider.setUserDetailsService(this);
		return authProvider;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> role) {
		return getGrantedAuthorities(getPrivileges(role));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Permission> collection = new ArrayList<>();
		roles.forEach(role -> collection.addAll(role.getPermissions()));
		collection.forEach(permission -> privileges.add(permission.getName()));
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
}
