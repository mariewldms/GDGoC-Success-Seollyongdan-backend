package com.example.seollyongbackend.service;

import com.example.seollyongbackend.entity.PostLike;
import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.entity.User;
import com.example.seollyongbackend.repository.PostLikeRepository;
import com.example.seollyongbackend.repository.CommunityPostRepository;
import com.example.seollyongbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("User already liked this post");
        }

        postLikeRepository.save(new PostLike(user, post));
    }

    @Transactional
    public void unlikePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        PostLike postLike = postLikeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalStateException("Like not found"));

        postLikeRepository.delete(postLike);
    }

    @Transactional(readOnly = true)
    public long getLikeCount(Long postId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return postLikeRepository.countByPost(post);
    }
}
