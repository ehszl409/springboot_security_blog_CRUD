<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>


<div class="container">
	<form>
  <div class="form-group">
  	<input type="hidden" id="id" value="${post.id}"/> 
    <input type="text" class="form-control" placeholder="Enter Title" name="title" id="title" value="${post.title}">
  </div>
  
  <div class="form-group">
    <textarea rows="" cols="5" class="form-control" id="content">
    	${post.content}
    </textarea>
  </div>
  
  <button id="btn-update" class="btn btn-primary">수정하기</button>
</form>
</div>

  <script>
      $('#content').summernote({
        tabsize: 2,
        height: 300
      });
      
      /* 리스너 방식. */
  	$("#btn-update").on("click",(e)=>{
  		/* form 태그의 action, submit을 안타게 막기 */
  		e.preventDefault();
  		
  		/* 자바스크립트 오브젝트 */
  		/* form에 적힌 데이터를 가져온다. */
  		let data ={
  			title:$("#title").val(),
  			content:$("#content").val()
  		}
  		
  		/* ajax를 위해서 post id값을 가져온다. */ 
  		/* 만약 ${post.id}이 값을 바로 스크립트안에 넣어버리면 (JSP코드를 넣으면) 
  		파일을 따로 분리하지 못한다. */
  		let id = $("#id").val();
  		
  		console.log(data);
  		
  		/* PUT,DELETE 요청을 할려면 ajax를 사용하자 */
  		$.ajax({
  			type:"PUT",
  			url:"/post/" + id,
  			data:JSON.stringify(data),
  			contentType:"application/json; charset=utf-8",
  			dataType:"json"
  		}).done((res)=>{
  			console.log(res);
  			if(res.statusCode === 1){
  				alert("수정에 성공하였습니다.");
  				history.go(-1);
  			}
  		});
  		
  	});
  	
      
    </script>

<%@ include file="../layout/footer.jsp" %>
