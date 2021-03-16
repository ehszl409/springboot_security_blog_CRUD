package com.park.blog.domain.reply;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.park.blog.domain.post.Post;
import com.park.blog.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reply {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 관계 : 댓글은 제일 자식 관계
	// 유저와 게시물 둘다 연관관계가 필요하다.
	// 연관 관계 부분 다시 보기.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	// 연관 관계 부분 다시 보기.
	@ManyToOne
	@JoinColumn(name = "postId")
	private Post post;

	@Column(nullable = false, length = 200)
	private String content;

	@CreationTimestamp
	private Timestamp createDate;

}
