<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>


<!-- 시큐리티는 x-www-form-url-encoded 타입만 인식 -->
<form>
	<input type="hidden" id="id" value="${id}"/>
	<input type="text" value="${princlpal.user.username}" id="username" placeholder="Username" readonly="readonly"/><br/>
	<input type="password" value="" id="password" placeholder="Password"/><br/>
	<input type="email" value="${princlpal.user.email}" id="email" placeholder="Email"/><br/>
	<button id="btn-update">회원가입</button>
</form>

<script>

	function update(){
		
	}

	$("#btn-update").on("click", (e)=>{
		e.preventdefault();
		let data = {
			username : $("#username").val()
			password : $("#password").val()
			email : $("#email").val()
		};
		
		let id = $("#id").val();
		
		$.ajax({
			type:"PUT",
			url:"/user/${}"
		})
		.done((res)=>{
			
		});
		
	});

</script>

<%@ include file="../layout/footer.jsp"%>