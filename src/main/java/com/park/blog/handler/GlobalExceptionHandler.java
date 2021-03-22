package com.park.blog.handler;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.park.blog.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// 인수에 잘못된 값이 들어왔을 경우 익셉션 처리
	@ExceptionHandler(value = IllegalArgumentException.class)
	public CMRespDto<?> illegalArgumentException(Exception e){
		return new CMRespDto<>(-1, e.getMessage());
	}
	
	// 요구한 원소가 없을 때 발생한다.
	@ExceptionHandler(value = NoSuchElementException.class)
	public CMRespDto<?> noSuchElementException(Exception e){
		return new CMRespDto<>(-1, "잘못된 ID입니다.");
	}
	
	
	@ExceptionHandler(value = MyAuthenticationException.class)
	public CMRespDto<?> myAuthenticationException(Exception e){
		return new CMRespDto<>(-1, "로그인 후 이용해주세요.");
	}
	


}
