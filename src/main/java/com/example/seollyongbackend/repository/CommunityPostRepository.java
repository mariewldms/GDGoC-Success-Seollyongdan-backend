package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//게시글을 데이터베이스에 저장하고 조회하는 JPA 저장소
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    @Modifying
    @Query("update CommunityPost p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateView(@Param("id") Integer id);

}
