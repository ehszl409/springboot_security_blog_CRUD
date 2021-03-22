package com.park.blog.web.reply;

import com.park.blog.domain.reply.Reply;

import lombok.Data;

@Data
public class ReplySaveReqDto {
	private String content;
	private Integer postId;
	
	public Reply toEntity() {
		return Reply.builder()
				.content(content)
				.build();
	}
}
