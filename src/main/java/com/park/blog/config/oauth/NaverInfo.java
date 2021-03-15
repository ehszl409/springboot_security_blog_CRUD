package com.park.blog.config.oauth;

import java.util.Map;

// 추상화 클래스를 상속하면 추상 메서드를 구현해줘야 한다.
// 이러한 방식을 통해서 소셜로그인 정보들을 오브젝트로 구현하도록
// 강제성을 부여할 수 있다.
public class NaverInfo extends OAuth2UserInfo{

	public NaverInfo(Map<String, Object> attributes) {
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getId() {
		// 키 값만 알면 데이터를 찾아 낼 수 있다.
		return (String) attributes.get("id");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String) attributes.get("name");
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String) attributes.get("email");
	}

	@Override
	public String getImageUrl() {
		// TODO Auto-generated method stub
		return "";
	}
	
	// 소셜로 회원가입한 정보가 있는지 판단하기 위해
	// 소셜이름_ + 고유ID 번호를 합성해서 리턴해주는 추상메서드 구현.
	@Override
	public String getUsername() {
		return "Naver_" + (String) attributes.get("id").toString();
	}

}
