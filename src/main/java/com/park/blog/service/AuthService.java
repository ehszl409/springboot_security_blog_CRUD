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
		System.out.println("회원가입 서비스 실행.");
		// 회원가입시 암호화된 패스워드로 세팅을 해줘야지 시큐리티에서
		// 회원가입을 동작하게 허락한다.
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

}
