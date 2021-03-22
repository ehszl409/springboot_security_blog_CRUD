package com.park.blog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{

	// countQuery : 쿼리 전체 갯수를 세어주는 속성.
	// 목적 : 페이징의 간편함
	@Query(value = "SELECT * FROM post WHERE title LIKE %:keyword% OR content LIKE %:keyword%",
			countQuery = "SELECT count(*) FROM post WHERE title LIKE %:keyword% OR content LIKE %:keyword%",
			nativeQuery = true)
	Page<Post> findByKeyword(String keyword, Pageable pageable);
}
