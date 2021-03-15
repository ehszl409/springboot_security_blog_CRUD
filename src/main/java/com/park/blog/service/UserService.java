package com.park.blog.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.blog.domain.user.RoleType;
import com.park.blog.domain.user.User;
import com.park.blog.domain.user.UserRepository;
import com.park.blog.web.user.dto.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 업데이트 할 땐 user의 id값과 데이터를 따로 받자.
	
	@Transactional
	public User 회원수정(int id, UserUpdateReqDto updateReqDto) {
		// 영속화를 시키면 영속성 컨텍스트에 오브젝트에 객체가 들어오게 된다.
		// 서비스가 끝나면 영속화가 끝나는데, 이 때 1차 캐시공간과 2차 캐시 공간을 비교 후 더티체킹을 한다.
		// 1차 캐시란 처음 영속화된 객체가 저장되는 곳이고
		// 2차 캐시란 1차 캐시에 저장된 객체에 변화가 생기면 변화된 값이 저장되는 곳이다.
		// 더티 체킹이란 1차 캐시과 2차 캐시를 비교해서 값이 수정이 되었다면 DB에 Flush(Commit)시켜 버린다.
		User userEntity = userRepository.findById(id).get();
		
		String encPass = bCryptPasswordEncoder.encode(updateReqDto.getPassword());
	
		userEntity.setPassword(encPass);
		userEntity.setEmail(updateReqDto.getEmail());
		
		
		// 세션 변경을 위해서 데이터값을 리턴 해줘야 한다.
		return userEntity;
	}
	
}
