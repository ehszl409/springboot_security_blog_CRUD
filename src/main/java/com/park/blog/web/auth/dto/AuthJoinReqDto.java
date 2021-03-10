package com.park.blog.web.auth.dto;



import com.park.blog.domain.user.User;

import lombok.Data;

// 나중에 Vaild 처리를 하자. 

@Data
public class AuthJoinReqDto {
	private String username;
	private String password;
	private String email;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
	}
	
}
