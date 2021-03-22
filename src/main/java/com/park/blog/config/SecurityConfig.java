package com.park.blog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.park.blog.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

//@Configuration
//@EnableWebSecurity
//시큐리티를 만들어 주기위해 필수적인 어노테이션 두가지 입니다.
//WebSecurityConfigurerAdapter을 상속 받는 이유는 어댑터를 상속해야지 
//내가 사용하고 싶은 추상메서드만 골라서 구현할 수 있습니다.
//예) 요리사는 칼이라는 추상 클래스를 상속하는데 칼 추상 메세드에는 공격이라는 추상 메서드가 있습니다.
//요리사는 요리를 위해 칼을 상속받는 것이기에 필요없는 추상 메서드를 구현할 필요가 없습니다.

// AuthenticationFilter 역할을 합니다.
// WebSecurityConfigurerAdapter를 사용하는 이유는 함수를 걸어준다. 
// = 모든 함수를 오버라이딩 하지 않아도 된다.
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 시큐리티를 재정의 한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuth2DetailsService oAuth2DetailsService;

	// @Bean은 Public으로 만들어야 된다.
	// 회원가입시 입력되는 패스워드는 필수적으로 암호화 해줘야한다.
	// 암호화 해주고 어떤 방식으로 암호화했는지 시큐리티한테 알려줘야한다.
	// 이유는 시큐리티를 사용하기 위해선 시큐리티가 정한 방식을 따라야 하기 때문이다.
	// IoC등록만 하면 AuthenticationManager가 BCrypt로 자동 검증해줌.
	// = 패스워드가 어떤 방식으로 암호화 되어 있는지 알려주는 방법은 IoC등록만 해주면 된다.
	@Bean
	public BCryptPasswordEncoder encode() {
		System.out.println("패스워드 암호화 실행됨.");
		return new BCryptPasswordEncoder();
	}

	// 시큐리티의 기능을 설정할 수 있는 추상 메서드입니다.
	// 시큐리티의 사용하는 기본원리는 모든 요청을 차단하고
	// 내가 필요한 요청들만 골라서 설정해주는 원리 입니다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("시큐리티 필터 실행됨.");
		// csrf토큰을 차단한다. 가짜인지 진짜인지 확인한다.
		http.csrf().disable();
		http
				// authorizeRequests()는 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미합니다.
				// HttpServletRequest는 Http프로토콜의 request 정보를 servlet에게 전달하기 위한 목적으로 사용
				// Header정보, Parameter, Cookie, URI, URL 등의 정보를 읽어들이는 메소드를 가진 클래스
				.authorizeRequests()

				// antMatchers는 시큐리티에서 설정하고 싶은 경로를 적는 곳입니다.
				// 아래와 같이 .access를 통해서 hasRole을 담아서
				// 꼭 ROLE_ + 권한 문법을 지켜줘야지 동작하는 것을 확인.
				.antMatchers("/user/**").access("hasRole('ROLE_USER')").antMatchers("/admin/**")
				.access("hasRole('ROLE_ADMIN')")

				// permitAll는 권한과 관계없이 모든 사용자가 접근 가능하도록 하는 것입니다.
				// 주소를 "/**" 해주지 않으면 너무 많은 리다이렉션을 호출했다는 오류 발생.
				.antMatchers("/**").permitAll()

				// 위에서 명시한 경로를 제외한 나머지 요청에 대해서는 인증을 요구하도록 설정.
				.anyRequest().authenticated().and()

				// form 태그 기반의 로그인을 지원하겠다는 설정입니다.
				// - 이를 이용하면 별도의 로그인 페이지를 제작하지 않아도 됩니다.
				// '/login' 경로를 호출하면 스프링 시큐리티에서 제공하는 기본 로그인 화면을 볼 수 있게 됩니다.
				.formLogin()

				// 시큐리티가 제공하는 로그인 페이지가 아니라 우리가 커스텀으로 만든
				// 로그인 페이지를 사용하겠다 라는 뜻입니다.
				.loginPage("/loginForm")

				// form의 action값과 일치 시켜줘야한다.
				// form의 값을 이용해서 로그인 처리하는 주소를 적어야한다.
				// = x-www-form-urlencoded, 
				//   시큐리티가 post로 온 /login 이라는 주소가 들어오면 낚아챔
				.loginProcessingUrl("/login")

				
//				.successHandler(new AuthenticationSuccessHandler() {
//					
//					@Override
//					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//							Authentication authentication) throws IOException, ServletException {
//						response.sendRedirect("/");
//						
//					}
//				});
				
				// 위의 successHandler와 비슷하지만,
				// 사용자가 갈려고 하는 페이지가 있을 때는 안 먹힘
				.defaultSuccessUrl("/").and().oauth2Login().userInfoEndpoint().userService(oAuth2DetailsService);
	}

}
