package com.park.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.blog.domain.post.Post;
import com.park.blog.domain.post.PostRepository;
import com.park.blog.domain.reply.Reply;
import com.park.blog.domain.reply.ReplyRepository;
import com.park.blog.domain.user.User;
import com.park.blog.web.reply.ReplySaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final PostRepository postRepo;
	
	@Transactional
	public Reply 댓글달기(ReplySaveReqDto replySaveReqDto, User user) {
		// RePly를 세팅해서 리턴해주기.
		
		// 익셉션 처리 해주기.
		Post postEntity = postRepo.findById(replySaveReqDto.getPostId()).orElseThrow(()->{
			return new IllegalArgumentException("잘못된 ID입니다.");
		});
		
		Reply reply = replySaveReqDto.toEntity();
		reply.setUser(user);
		reply.setPost(postEntity);
		
		Reply replyEntity = replyRepository.save(reply);
		
		return replyEntity;
	}
	
	@Transactional
	public int 삭제하기(int id, int userId) {
		Reply replyEntity = replyRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("잘못된 ID입니다.");
		});
		
		if(replyEntity.getUser().getId() == userId) {
			replyRepository.deleteById(id);
			return 1;
		} else {
			return -1;
		}
		
	}
}
