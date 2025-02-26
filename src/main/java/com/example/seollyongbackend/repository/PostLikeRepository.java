package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.PostLike;
import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserAndPost(User user, CommunityPost post);

    Optional<PostLike> findByUserAndPost(User user, CommunityPost post);

    long countByPost(CommunityPost post);
}
