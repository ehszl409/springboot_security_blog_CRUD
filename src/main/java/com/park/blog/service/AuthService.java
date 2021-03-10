package com.park.blog.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.blog.domain.user.RoleType;
import com.park.blog.domain.user.User;
import com.park.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

// 인증만 관리하는 서비스
@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

}
