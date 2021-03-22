package com.park.blog.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.park.blog.config.auth.PrincipalDetails;
import com.park.blog.domain.reply.Reply;
import com.park.blog.domain.reply.ReplyRepository;
import com.park.blog.service.ReplyService;
import com.park.blog.web.dto.CMRespDto;
import com.park.blog.web.reply.ReplySaveReqDto;

import lombok.RequiredArgsConstructor;


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
	
	@PostMapping("/reply")
	public CMRespDto<?> save(@RequestBody ReplySaveReqDto replySaveReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("댓글 쓰기 요청 진입 데이터 : " + replySaveReqDto);
	
		Reply replyEntity = replyService.댓글달기(replySaveReqDto, principalDetails.getUser());
		
		if(replyEntity == null) {
			return new CMRespDto<>(-1, null);
		} else {
			return new CMRespDto<>(1, replyEntity);
		}
	}
}
