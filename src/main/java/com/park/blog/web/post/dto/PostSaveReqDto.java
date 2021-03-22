package com.park.blog.web.post.dto;

import javax.validation.constraints.NotBlank;

import com.park.blog.domain.post.Post;

import lombok.Data;

@Data
public class PostSaveReqDto {
	// Validation 체크는 DTO에 달아줍니다.
	// 이미 모델화를 통해 Nullable 설정을 했지만 persistence는 DB와 영속성 컨텍스트에
	// 설정한 제약 조건이고 Validation은 DTO로 통신할 때 설정하는 제약 조건으로
	// 모두 걸어 주는것이 좋습니다.
	@NotBlank(message = "제목을 입력해주세요.")
	private String title;
	
	@NotBlank(message = "내용을 입력해주세요.")
	private String content;

	public Post toEntity() {
		return Post.builder().title(title).content(content).build();
	}
}