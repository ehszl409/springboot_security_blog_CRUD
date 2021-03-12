package com.park.blog.config.oauth;

import java.util.Map;

//목적 : PrincipalDetails를 리턴 해줘야 한다.
// 이유 : 어디에서든지 PrincipalDetails를 호출하면 유저 정보 값이 다 나오게 할려고 
// 방법 : oAuth2User들고있는 getAttributes값만 Map에 넘겨주고 리턴 시켜준다.
//			나머지 값은 userRequest가 Attribute로 들고있는 값을 통해서 회원가입을 시켜버린다.

//1. 통합 추상 클래스를 생성.
// = 서로 다른 소셜 로그인을 하면 Attributes 정보 다르다.
// 기본적으로 데이터값을 처리할 땐 자바오브젝트를 만들어서 처리하는 것이 편리한데,
// 서로 다른 Attribute의 속성 이름들 때문에 활용을 하기가 힘들다
// 그래서 통합시켜주는 클래스를 만들어서 관리하는 것이다.
public abstract class OAuth2UserInfo {
	protected Map<String, Object> attributes;
	
	// 생성자.
	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	// getter
	public Map<String, Object> getAttributes(){
		return attributes;
	}
	 
	// 페이스북과 구글은 고유번호에 대한 ID이름이 서로다르다.
	// 그래서 추상클래스를 구현하면서 자신의 이름 값을 직접넣어두면 
	// 나는 똑같은 함수을 호출해도 레퍼런스에 따라 값이 다르게 출력되게 하는 것이다.
	// 한마디로 모두 같은 틀을 사용하도록 하는것.
	// 내가 필요로하는 값만 만들어 준다.
	public abstract String getId();
	public abstract String getName();
	public abstract String getEmail();
	public abstract String getImageUrl();
	public abstract String getUsername();
	
	
}
