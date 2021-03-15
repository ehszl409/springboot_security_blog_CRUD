package com.park.blog.web.user.dto;

import lombok.Data;


@Data
public class UserUpdateReqDto {
	private String username;
	private String password;
	private String email;
	
	// toEntity를 안만드는 이유는 데티체킹을 할 것이기 때문이다.
}
