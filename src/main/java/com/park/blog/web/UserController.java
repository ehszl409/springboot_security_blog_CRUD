package com.park.blog.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.blog.config.auth.PrincipalDetails;
import com.park.blog.domain.user.User;

import lombok.RequiredArgsConstructor;

// AuthController : 로그인, 회원가입, 로그아웃, 회원수정
// UserController : 전체찾기, 한건찾기


@Controller
public class UserController {
	
	@GetMapping("/user/{id}")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("id",id);
		return "user/updateForm";
	}
	
	
	//@Controller + @ResponseBody = @RestController
	@GetMapping("/user")
	public @ResponseBody String findAll(@AuthenticationPrincipal PrincipalDetails principalDetails) { 
//		// 로그인시 getAuthentication 객체를 만드는것이 목표.
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//		
//		// getPrincipal().getUser() : user 오브젝트를 찾을 수 있다.
//		System.out.println("getPrincipal : " + principalDetails.getUser());
		System.out.println("USER 오브젝트: " + principalDetails.getUser());
		return principalDetails.getUser().toString(); // 데이터를 리턴 (MessageConverter 발동)
	}
	
}
