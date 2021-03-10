package com.park.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.park.blog.service.AuthService;
import com.park.blog.web.auth.dto.AuthJoinReqDto;

import lombok.RequiredArgsConstructor;

// 시큐리티를 쓰는 목적 : 인증과 권한처리를 간편하게 하기 위해서이다.
// 로그인을 하면 세션이 만들어지는데 그 세션을 컨트롤러에서 관리하지 않고
// 시큐리티가 관리하게 해서 인증과 권한처리를 간편하게 한다.
@RequiredArgsConstructor
@Controller
public class AuthController {

	private final AuthService authService;

	// 주소 : /user, /post, /loginForm
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("로그인 페이지로 이동.");
		return "auth/loginForm"; // ViewResolver 발동
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		System.out.println("회원가입 페이지로 이동.");
		return "auth/joinForm"; // ViewResolver 발동
	}
	
	// /login은 컨트롤러에서 관리하지않고 시큐리티로 넘겨줘서
	// 세션을 시큐리티가 관리하도록 한다.
	

	// 굳이 @RequestBody를 붙여서 json으로 파싱할 필요없이 x-www로 받아도 된다.
	// 왜냐하면 폼으로 들어오는 데이터여서
	@PostMapping("/join")
	public String join(AuthJoinReqDto authJoinReqDto) {
		System.out.println("회원가입 컨트롤러 실행됨.");
		authService.회원가입(authJoinReqDto.toEntity());
		return "redirect:/loginForm";
	}

}
