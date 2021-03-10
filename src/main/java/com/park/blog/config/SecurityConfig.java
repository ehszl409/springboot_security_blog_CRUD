package com.park.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// AuthenticationFilter 
// WebSecurityConfigurerAdapter를 사용하는 이유는 함수를 걸어준다. 
// = 모든 함수를 오버라이딩 하지 않아도 된다.
@Configuration
@EnableWebSecurity // 시큐리티를 재정의 한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("시큐리티 필터 실행됨.");
		http.csrf().disable(); // csrf토큰을 차단한다 // 가짜인지 진짜인지 확인한다.
		http.authorizeRequests()
				// 막고 싶은 주소
				// 403오류 발생 = Forbidden 접근 권한 없음.
				// ROLE_은 시큐리티가 요구하는 필수 사항이므로 지켜야한다.
				.antMatchers("/user", "/post").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
				.anyRequest()
				.permitAll()
				.and()
				.formLogin() // x-www-form-urlencoded 타입만 받는다. (json사용 불가)
				.loginPage("/loginForm") // 위 주소가 요청되면 "/login"으로 자동으로 redirection
				.loginProcessingUrl("/login"); // 위 주소로 요청되면 시큐리티가 낚아챈다.
	}

}
