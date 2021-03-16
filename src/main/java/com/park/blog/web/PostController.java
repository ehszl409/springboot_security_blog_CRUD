package com.park.blog.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.blog.config.auth.PrincipalDetails;
import com.park.blog.domain.post.Post;
import com.park.blog.service.PostService;
import com.park.blog.web.dto.CMRespDto;
import com.park.blog.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
	
	// 메인페이지 주소
	@GetMapping("/")
	public String findAll(Model model, 
			@PageableDefault(sort = "id", 
			direction = Sort.Direction.DESC, 
			size = 3) Pageable pageable, 
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		/*
		 * System.out.println("어떤 소셜로 로그인을 했을까?? (null이라면 소셜 로그인이 아닙니다.)");
		 * System.out.println("0. isOAuth : " + principalDetails.isOAuth());
		 * System.out.println("1. Attribute : " + principalDetails.getAttributes());
		 * System.out.println("2. Username : " +
		 * principalDetails.getUser().getUsername());
		 */
		
		Page<Post> posts = postService.전체찾기(pageable);
     	// RequestDispatcher = Model
     	model.addAttribute("posts",posts);
		return "post/list";
	}
	
	@GetMapping("/post/saveForm")
	public String saveForm() {
		return "post/saveForm";
	}
	
	@GetMapping("/post/{id}/updateForm/")
	public String updateForm(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post", postEntity);
		return "post/updateForm";
	}
	
	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post", postEntity);
		return "post/detail";
	}
	
	// 데이터를 리턴하는 경우엔 모두 @ResponseBody를 붙여줘야 한다.
	@PutMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @RequestBody PostSaveReqDto postSaveReqDto) {
		postService.수정하기(id, postSaveReqDto);
		return new CMRespDto<>(1, null);
	}
	
	@DeleteMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable int id){
		postService.삭제하기(id);
		return new CMRespDto<>(1, null);
	}
	
	@PostMapping("/post")
	public String save(PostSaveReqDto postSaveReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("글쓰기 요청 진입.");
		// 영속화 x
		Post post = postSaveReqDto.toEntity();
		// user을 넣어주지 않으면 DB에 userId가 null로 된다.
		post.setUser(principalDetails.getUser());
		
		// 영속화 o
		Post postEntity = postService.글쓰기(post);
		System.out.println("postEntity : " + postEntity);
		
		if(postEntity == null) {
			return "post/saveForm";
		}else {
			return "redirect:/";
		}
	}
}
