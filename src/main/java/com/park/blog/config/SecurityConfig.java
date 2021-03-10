package com.park.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// WebSecurityConfigurerAdapter를 사용하는 이유는 함수를 걸어준다. 
// = 모든 함수를 오버라이딩 하지 않아도 된다.
@Configuration
@EnableWebSecurity // 시큐리티를 재정의 한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter { 
	
	// IoC등록만 하면 AuthenticationManager가 BCrypt로 자동 검증해줌.
	// = 패스워드가 어떤 방식으로 암호화 되어 있는지 알려주는 방법은 IoC등록만 해주면 된다.
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // csrf토큰을 차단한다	// 가짜인지 인자인지 확인한다.
		http.authorizeRequests()
			// 막고 싶은 주소
			// 403오류 발생 = Forbidden 접근 권한 없음.
			.antMatchers("/user","/post").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")// USER 권한이 있어야 post에 접근할 수 있다.
			.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin() // x-www-form-urlencoded 타입만 받는다. (json사용 불가)
			.loginPage("/loginForm") // 위 주소가 요청되면 "/login"으로 자동으로 redirection
			.loginProcessingUrl("/login"); // 위 주소로 요청되면 시큐리티가 낚아챈다.
	}
	
}
