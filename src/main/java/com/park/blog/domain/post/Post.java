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
	
	// 순방향 맵핑.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	// 해당 데이터는 들고가지 않는다. 
	// getter가 호출되기 전까진 LAYZ가 나타나지 않는다.
	// 관계 : Reply와의 관계는 Post기준으로 OneToMany이다.
	// mappedBy = "Reply속 Post의 변수명" (꼭 기억) : 변수명은 참조키의 주인이 아니니 
	//				이 속성으로 설정된 어노테이션된 변수를 참조키로 만들지마. 
	// @???ToMany는 기본적으로 FetchType.LAZY이다. @ManyTo???는 기본적으로 FetchType.EAGER이다.
	// FetchType.LAZY : 지연 로딩이라는 개념으로 LAZY로 설정되어 있는 참조 변수는 부모 관계 또는 이 변수를 필드로 가지고있는 객체를
	//					select할 때 select가 되지않고 참조 변수 속 필드를 직접 조회(getter 호출)할 때
	//					select가 되어 DB가 조회된다. 
	// FetchType.EAGER : 즉시 로딩이라는 개념으로 부모 관계 또는 이 참조값을 필드로 가지고 있는 객체를 호출할 때 조인문이 만들어져서
	//					함께 값을 select 하게 된다.
	// cascade = CascadeType.REMOVE : 부모 관계 또는 이 참조값을 필드로 가지고 있는 객체를 삭제할 때 참조 변수값을 함께 삭제할 것인지
	//									설정한다. REMOVE=삭제 PERSIST=유지.
	@OneToMany(mappedBy = "post",  fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	
	// 참조 변수 속 자신의 객체를 다시 호출하는 무한 맵핑이 되는 관계를 멈추는것.
	@JsonIgnoreProperties({"post"})
	
	// 맵핑된 참조 테이블이 select될 때 설정을 해준다.
	@OrderBy("id desc")
    private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}
