package com.nmpc.kindergarten.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nmpc.kindergarten.model.User;
import com.nmpc.kindergarten.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String playCenterId) throws UsernameNotFoundException {
		User user = userRepository.findByPlayCenterId(playCenterId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with PlayCenter ID: " + playCenterId));

		return new org.springframework.security.core.userdetails.User(user.getPlayCenterId(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList()));
	}

}
