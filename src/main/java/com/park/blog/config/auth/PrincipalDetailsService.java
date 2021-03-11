package com.park.blog.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.park.blog.domain.user.User;
import com.park.blog.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	
	// 오로지 username으로만 인증절차를 거친다. 
	// 패스워드없이도 시큐리티가 자동으로 검증해준다.
	// 여기서 개발자가 구현해주면 나머지는 시큐리티가 전부 해준다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserDetailsService 실행됨 /login이 호출 되면 자동으로 실행되어 username이 DB에 있는지 확인한다.");
		// 우리가 구현해야하는 내용
		User principal = userRepository.findByUsername(username);
		
		if(principal == null) {
			return null;
		} else {
			
			// 리턴해주는 타입이 UserDetails를 상속받고 있다.
			return new PrincipalDetails(principal);
			
		}
	}
	
}
