package com.park.blog.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.park.blog.domain.user.User;
import com.park.blog.handler.MyAuthenticationException;

// 세션인증을 인터셉터로 분리하기.
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// 0. 인터셉터 만들기
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 1. 인터셉터 등록하기
		registry.addInterceptor(new HandlerInterceptor() {
			
			// 2. 인터셉터 동작 순간 정하기
			// preHandle : 컨트롤러 실행 직전에 동작
			// 				반환값이 true 일 경우 정상적 진행
			// 				반환값이 false면 컨트롤러 진입 안함.
			// 				throw 익셉션 처리.
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				
				// 3. 내용 구현하기 
				HttpSession session = request.getSession();
				User principal = (User) session.getAttribute("principal");
				String method = request.getMethod();
				
				// 로그인 하지 않고 인증이 필요한 요청이 들어오면 익셉션 처리한다.
				if(principal == null && (method.equals("PUT")) || (method.equals("POST")) || 
						(method.equals("DELETE")) ) {
					throw new MyAuthenticationException();
				} else {
					return true;
				}
			}
			// 4. 인터셉터 동작 시점 설정
		}).addPathPatterns("/user/*").addPathPatterns("/post/*").addPathPatterns("/reply/*");
	}
	
}
