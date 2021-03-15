<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>


<!-- 시큐리티는 x-www-form-url-encoded 타입만 인식 -->
<form>
	<input type="hidden" id="id" value="${id}" /> <!-- 페이지를 받아오기 위해 id값을 설정 -->
	<input type="text" value="${principal.user.username}" id="username" placeholder="Username" readonly="readonly"/><br/>
	<input type="password" value="" id="password" placeholder="Password"/><br/>
	<input type="email" value="${principal.user.email}" id="email" placeholder="Email"/><br/>
	<button id="btn-update">수정하기</button>
</form>

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

<%@ include file="../layout/footer.jsp"%>