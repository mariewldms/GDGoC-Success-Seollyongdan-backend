package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

//게시글을 데이터베이스에 저장하고 조회하는 JPA 저장소
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

}
