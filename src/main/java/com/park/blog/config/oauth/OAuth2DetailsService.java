package com.park.blog.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {
	
	@Override
		public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
			System.out.println("OAuth 로그인 진행 중....");
			System.out.println("OAuth로 받은 토큰 : " + userRequest.getAccessToken().getTokenValue());
			
			// DefaultOAuth2UserService을 상속해줘야지 loadUser를 사용할 수 있다.
			// 이 코드의 목적은 토큰으로 OAuth서버에 사용자 정보를 요청하는 것.
			OAuth2User oAuth2User = super.loadUser(userRequest);
			
			System.out.println("OAuth2User : " + oAuth2User.getAttributes());
			
		
			return processOAuth2User(userRequest, oAuth2User);
		}
	
	// 구글 로그인 프로세스
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
		// 1번 통합 클래스를 생성.
		// = 서로 다른 소셜 로그인을 하면 getAttributes 내용이 다르다.
		return null;
	}
}
