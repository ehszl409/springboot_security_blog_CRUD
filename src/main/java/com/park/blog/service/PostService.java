package com.park.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.blog.domain.post.Post;
import com.park.blog.domain.post.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	
	public List<Post> 전체찾기(){
		return postRepository.findAll();
	}
	
	@Transactional
	public Post 글쓰기(Post post) {
		return postRepository.save(post);
	}
}
