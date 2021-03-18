# 스프링부트 블로그 프로젝트
#### 03.18 최신화

## 의존성
- Spring Boot DevTools
- Lombok
- Spring Data JPA
- MySQL Driver
- Spring Security
- OAuth2 Client
- Spring Web
- Tomcat_jasper
- JSTL
- Spring_security_taglibs

## DB 설정
```sql
	create user 'pos'@'%' identified by 'pos1234'; 
	GRANT ALL PRIVILEGES ON *.* TO 'pos'@'%'; 
	create database pos; 
```

## 세션가져오기
```jsp
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

	<!-- 시큐리티에서 세션을 확인하는 방법 -->
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="principal"/>
	</sec:authorize>
```

## OAuth 2.0 로그인 설정
``` java
	   -----------[네이버]--------------
  security:
    oauth2:
      client:
        registration:
          naver:
            client-id: 29huOlkKoYHKSd0x4RCQ
            client-secret: dnVKkPc4oj
            scope:
            - id
            - name
            - email
            
            # 스프링에서 지원해는 OAuth로그인 말고는 모두 명시해줘야 한다.
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/naver
            client-name: Naver
            
        provider:
         naver:
            authorization-uri:  https://nid.naver.com/oauth2.0/authorize
            token-uri:  https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response
            
            
            
     
           -----------[카카오]--------------
       
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: 51179ac1057a3e70c0c3a469eb45fde2
            client-secret: 98dGsHd1eSbgkLr2F77REfRnNq9sIwZk
            scope:
            - profile
            - account_email
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
            client-name: Kakao
            # 카카오톡 로그인 
            client-authentication-method: POST
            
            
        provider:
         kakao:
            authorization-uri:  https://kauth.kakao.com/oauth/authorize
            token-uri:  https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
```

## 회원정보 수정하기 구현방법.
```java
	-------------[Service]-------------
	@Transactional
	public User 회원수정(int id, UserUpdateReqDto updateReqDto) {
		// 영속화를 시키면 영속성 컨텍스트에 오브젝트에 객체가 들어오게 된다.
		// 서비스가 끝나면 영속화가 끝나는데, 이 때 1차 캐시공간과 2차 캐시 공간을 비교 후 더티체킹을 한다.
		// 1차 캐시란 처음 영속화된 객체가 저장되는 곳이고
		// 2차 캐시란 1차 캐시에 저장된 객체에 변화가 생기면 변화된 값이 저장되는 곳이다.
		// 더티 체킹이란 1차 캐시과 2차 캐시를 비교해서 값이 수정이 되었다면 DB에 Flush(Commit)시켜 버린다.
		User userEntity = userRepository.findById(id).get();
		
		String encPass = bCryptPasswordEncoder.encode(updateReqDto.getPassword());
	
		userEntity.setPassword(encPass);
		userEntity.setEmail(updateReqDto.getEmail());
		
		
		// 세션 변경을 위해서 데이터값을 리턴 해줘야 한다.
		return userEntity;
	}
	
	-------------[Controller]-------------
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
```
```JSP
	-------------[UpdateForm]-------------
	<form>
	<input type="hidden" id="id" value="${id}" /> <!-- 페이지를 받아오기 위해 id값을 설정 -->
	<input type="text" value="${principal.user.username}" id="username" placeholder="Username" readonly="readonly"/><br/>
	<input type="password" value="" id="password" placeholder="Password"/><br/>
	<input type="email" value="${principal.user.email}" id="email" placeholder="Email"/><br/>
	<button id="btn-update">수정하기</button>
	</form>
	
	-------------[Script]-------------
	<script>
	/* 리스너 */
	$("#btn-update").on("click",(e)=>{
		/* form 태그의 action, submit을 안타게 막기 */
		e.preventDefault();
		
		/* 자바스크립트 오브젝트 */
		/* form에 적힌 데이터를 가져온다. */
		let data ={
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		}
		
		/* ajax를 위해서 user id값을 가져온다. */ 
		let id = $("#id").val();
		
		console.log(data);
		
		/* PUT,DELETE 요청을 할려면 ajax를 사용하자 */
		$.ajax({
			type:"PUT",
			url:"/user/" + id,
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
		}).done((res)=>{
			console.log(res);
			if(res.statusCode === 1){
				alert("수정에 성공하였습니다.");
				location.href="/";
			}
		});
		
	});
	</script>
```

