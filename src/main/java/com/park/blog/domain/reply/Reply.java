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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
	// 목적 : Post를 select하면 Post,User,Reply 모두 다 같이 보고싶다.
	// User와 Post모두 ManyToOne으로 참조키의 주인은 Reply이다.
	// 하지만 Reply 자체를 select하는 경우는 우리 프로젝트에 없다.
	// 방법 : 그래서 순방향 맵핑이 아닌 역방향 맵핑을 해줘야한다.
	// 순방향 맵핑 : 참조키의 주인인 맵핑을 한것을 말한다.
	// 역방향 맵핑 : 참조키의 주인은 아니지만 select를 하고 싶다는 것.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	// 연관 관계 부분 다시 보기.
	// Post에서 Reply를 호출해도 여기는 
	// @JsonIgnoreProperties({"post"})으로 무한맵핑을 막아놔서
	// 호출되지 않는다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	private Post post;

	@Column(nullable = false, length = 200)
	private String content;

	@CreationTimestamp
	private Timestamp createDate;

}
