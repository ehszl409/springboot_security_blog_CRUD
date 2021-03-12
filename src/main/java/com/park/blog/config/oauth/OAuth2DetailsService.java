package com.park.blog.config.oauth;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.park.blog.config.auth.PrincipalDetails;
import com.park.blog.domain.user.User;
import com.park.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepo;
	
	
	@Override
		public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
			System.out.println("OAuth 로그인 진행 중....");
			System.out.println("OAuth로 받은 토큰 : " + userRequest.getAccessToken().getTokenValue());
			
			// DefaultOAuth2UserService을 상속해줘야지 loadUser를 사용할 수 있다.
			// 이 코드의 목적은 토큰으로 OAuth서버에 사용자 정보를 요청하는 것.
			OAuth2User oAuth2User = super.loadUser(userRequest);
			
			System.out.println("OAuth2User : " + oAuth2User.getAttributes());
			
			// 목적 : PrincipalDetails를 리턴 해줘야 한다.
			// 이유 : 어디에서든지 PrincipalDetails를 호출하면 유저 정보 값이 다 나오게 할려고 
			// 방법 : oAuth2User들고있는 getAttributes값만 Map에 넘겨주고 리턴 시켜준다.
			//			나머지 값은 userRequest가 Attribute로 들고있는 값을 통해서 회원가입을 시켜버린다.
			
			return processOAuth2User(userRequest, oAuth2User);
		}
	
	// 구글 로그인 프로세스
	// 왜 OAuth2UserRequest와 OAuth2User의 값을 인자로 받은 이유는 서로 값이 다르기 때문이다.
	// OAuth2UserRequest은 AccessToken 정보를 가지고 있다. (프로바이더 정보, 클라이언트 아이디 등등) 
	// OAuth2User은 유저 정보를 가지고있다. (name, Attribute 등등)
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
		//1. 통합 추상 클래스를 생성.
		// = 서로 다른 소셜 로그인을 하면 Attributes 정보 다르다.
		// 기본적으로 데이터값을 처리할 땐 자바오브젝트를 만들어서 처리하는 것이 편리한데,
		// 서로 다른 Attribute의 속성 이름들 때문에 활용을 하기가 힘들다
		// 그래서 통합시켜주는 클래스를 만들어서 관리하는 것이다.
		
		// Attribute값을 저장하기전 어떤 소셜로 로그인 되었는지 구분해야한다.
		// 그래서 OAuth2UserRequest을 이용해서 값을 찾아낸다.
		System.out.println("요청된 소셜 로그인 : " + userRequest.getClientRegistration().getClientName());
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getClientName().equals("Google")) {
			oAuth2UserInfo = new GoogleInfo(oAuth2User.getAttributes());			
		} else if(userRequest.getClientRegistration().getClientName().equals("Facebook")) {
			
		}
		
		//2. 최초 로그인 : 회원가입 + 로그인 / 기존 회원 : 로그인 
		// 으로 구분짓어서 회원가입을 시킬지 로그인을 시킬지 구분짓는다.
		// 회원가입을 유일하게 구분짓는 방법으론 고유ID를 사용한다.
		User userEntity = userRepo.findByUsername(oAuth2UserInfo.getUsername());
		System.out.println("userEntity : " + userEntity);
		
		// 회원가입시 패스워드가 null로 설정되면 안되기에
		// 아무도 모르는 랜덤 난수를 암호화해서 세팅해줘서 보완을 철처하게 한다.
		UUID uuid = UUID.randomUUID();
		String encPassword = new BCryptPasswordEncoder().encode(uuid.toString());
		
		 if(userEntity == null) {
			 // 회원가입을 먼저 해야하는 경우.
			 // 회원가입시 시큐리티에서 패스워드는 꼭 필요로하기에 넣어줘야한다.
			 User user = User.builder()
					 .username(oAuth2UserInfo.getUsername())
					 .password(encPassword)
					 .email(oAuth2UserInfo.getEmail())
					 .build();
					 
			 userEntity = userRepo.save(user);
			 return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
			 
		 } else {
			 // 이미 회원가입을 한 경우.(원래는 구글정보가 변경될 수 있기 떄문에 update를 해야됨(지금은 미구현).
			 // 사실 Attribute는 OAuth2UserInfo에서 원하는 값만 골라서 받을 때 사용되고 이 후에는 필요없지만,
			 // 후에 사용하게 될 경우가 생길지 모르기에 UserDetails에 담아준다.
			 return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
			 
		 }
		
	}
}
