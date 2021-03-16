package com.park.blog.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.park.blog.domain.post.Post;
import com.park.blog.domain.post.PostRepository;
import com.park.blog.domain.reply.Reply;
import com.park.blog.domain.reply.ReplyRepository;
import com.park.blog.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReplyControllerTest {

	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;
	
	// 게시글 상세보기(user, post, reply들)이 나와야 한다.
	
	@GetMapping("/reply")
	public String findAll() {
		System.out.println("전체 보기 실행.");
		List<Reply> replys = replyRepository.findAll();
		List<Post> posts = postRepository.findAll();
		return  "결과 : " + replys + posts;
	}
	
}
