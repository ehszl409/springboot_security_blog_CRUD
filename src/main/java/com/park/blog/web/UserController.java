package com.park.blog.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.blog.config.auth.PrincipalDetails;
import com.park.blog.domain.user.User;
import com.park.blog.service.UserService;
import com.park.blog.web.dto.CMRespDto;
import com.park.blog.web.user.dto.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

// AuthController : 로그인, 회원가입, 로그아웃, 회원수정
// UserController : 전체찾기, 한건찾기

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	// 유저정보 페이지로 이동하는 컨트롤러.
	// 시큐리티를 사용하기 때문에 인증이 되어 있는 상태이기 때문에 값을 들고 오지 않아도 된다.
	// id : user의 id값을 넘겨줘야 한다.
	@GetMapping("/user/{id}")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("id",id);
		return "user/updateForm";
	}
	
	// 수정하기 
	@PutMapping("/user/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @RequestBody UserUpdateReqDto updateReqDto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 영속화
		User userEntity = userService.회원수정(id, updateReqDto);
		
		// 세션을 변경하기 위해 UserDetails를 가지고 와서 User값을 바꿔서 넣어준다.
		principalDetails.setUser(userEntity);
		
		/*
		 * 세션 변경하는 다른 방법 UsernamePasswordToken에 변경된 값을 넣어 -> Authentication 객체로 만들어서 ->
		 * "시큐리티 컨텍스트 홀더" 에 집어 넣으면 됨. Authentication authentication = new
		 * UsernamePasswordAuthenticationToken(userEntity.getUsername(),
		 * userEntity.getPassword());
		 * SecurityContextHolder.getContext().setAuthentication(authentication);
		 */
		
		return new CMRespDto<>(1, null);
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
