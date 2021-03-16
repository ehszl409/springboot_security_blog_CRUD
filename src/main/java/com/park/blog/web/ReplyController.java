package com.park.blog.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.park.blog.config.auth.PrincipalDetails;
import com.park.blog.service.ReplyService;
import com.park.blog.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

/*
 * 해야하는것.
 * 1. 댓글쓰기 완료
 * 2. 디자인 변경.
 * 3. ControlAdvice Exception 처리 CMRespDto(-1)
 * 4. BindingResult 벨리데이션 체크 (보너스)
 * 5. 검색 (보너스)
 * */




@RequiredArgsConstructor
@RestController
public class ReplyController {

	private final ReplyService replyService;
	
	@DeleteMapping("/reply/{id}")
	public CMRespDto<?> delete(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
		// 모든 Entity 모든 Controller의 삭제와 수정은 
		// 동일인물이 로그인 했는지 확인을 해야한다.
		int result = replyService.삭제하기(id, principalDetails.getUser().getId());
		return new CMRespDto<>(result, null);
	}
}
