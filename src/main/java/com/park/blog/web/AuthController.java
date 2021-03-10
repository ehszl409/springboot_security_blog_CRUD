package com.park.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.park.blog.service.AuthService;
import com.park.blog.web.auth.dto.AuthJoinReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {

	private final AuthService authService;

	// 주소 : /user, /post, /loginForm
	@GetMapping("/loginForm")
	public String loginForm() {
		return "auth/loginForm"; // ViewResolver 발동
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "auth/joinForm"; // ViewResolver 발동
	}

	// 굳이 @RequestBody를 붙여서 json으로 파싱할 필요없이 x-www로 받아도 된다.
	// 왜냐하면 폼으로 들어오는 데이터여서
	@PostMapping("/join")
	public String join(AuthJoinReqDto authJoinReqDto) {
		authService.회원가입(authJoinReqDto.toEntity());
		return "redirect:/loginForm";
	}

}
