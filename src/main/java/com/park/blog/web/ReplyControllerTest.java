package com.park.blog.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.park.blog.domain.post.Post;
import com.park.blog.domain.post.PostRepository;
import com.park.blog.domain.reply.Reply;
import com.park.blog.domain.reply.ReplyRepository;
import com.park.blog.domain.user.User;
import com.park.blog.domain.user.UserRepository;
import com.park.blog.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReplyControllerTest {

	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	// 게시글 상세보기(user, post, reply들)이 나와야 한다.
	
	@GetMapping("/test/{id}")
	public CMRespDto<?> testPost(@PathVariable int id){
		//List<Post> post = postRepository.findAll();
		//User user = userRepository.findById(id).get();
		Reply reply = replyRepository.findById(id).get();
		//Post post = postRepository.findById(id).get();
		return new CMRespDto<>(1, reply);
	}
	
	
}
