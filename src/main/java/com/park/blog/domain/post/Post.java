package com.park.blog.domain.post;



import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.park.blog.domain.reply.Reply;
import com.park.blog.domain.user.RoleType;
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
public class Post {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	private String content;
	
	// 조회수
	@ColumnDefault("0")
	private int count;
	
	// 연관 관계 부분 다시 보기.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToMany(mappedBy = "post",  fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"post"})
	@OrderBy("id desc")
    private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
