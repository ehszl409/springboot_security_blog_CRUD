<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블로그</title>
</head>
<body>
<h1>로그인 페이지 입니다.</h1>
<hr/>
<!-- 시큐리티는 x-www-form-url-encoded 타입만 인식 -->
<form action="/login" method="post">
	<input type="text" name="username" placeholder="Username"/>
	<input type="password" name="password" placeholder="Password"/>
	<button>로그인</button>
</form>
아직 회원이 아니신가요?<a href="/joinForm">회원가입</a>
 <a href="/oauth2/authorization/google">구글 로그인</a>
 <a href="/oauth2/authorization/facebook">페이스북 로그인</a>
 <a href="/oauth2/authorization/github">깃허브 로그인</a>
</body>
</html>