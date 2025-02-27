package com.example.seollyongbackend.service;

import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.entity.User;
import com.example.seollyongbackend.repository.CommunityPostRepository;
import com.example.seollyongbackend.repository.UserRepository;
import com.example.seollyongbackend.dto.CommunityPostRequest;
import com.example.seollyongbackend.dto.CommunityPostDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityPostService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;


    public CommunityPostService(CommunityPostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // ✅ 특정 동네의 게시글만 조회하는 메서드 추가
    public List<CommunityPostDTO> getPostsByDistrict(String postDistrict) {
        List<CommunityPost> posts = postRepository.findByPostDistrict(postDistrict);
        return posts.stream().map(CommunityPostDTO::new).collect(Collectors.toList());
    }

    public CommunityPost createPost(CommunityPostRequest request) {
        User user = userRepository.findByNickname(request.getNickName());

        // 🔥 null 체크 추가
        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        boolean isResident = user.getTown().equals(request.getPostDistrict());

        CommunityPost post = new CommunityPost(
                request.getNickName(),
                user.getTown(),
                request.getPostDistrict(),
                isResident,
                request.getTitle(),
                request.getContent()
        );

        return postRepository.save(post);
    }

    //게시글 수정 로직
    @Transactional
    public CommunityPost updatePost(Long id, CommunityPostRequest request) {
        CommunityPost post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return postRepository.save(post);
    }

    //게시글 삭제 로직
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("삭제할 게시글이 없습니다.");
        }
        postRepository.deleteById(id);
    }

    //게시글 단일 조회 (조회 수 증가 포함)
    @Transactional
    public CommunityPostDTO getPostById(Long id) {
        CommunityPost post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 🔥 조회 수 증가
        post.setViewCount(post.getViewCount() + 1);

        return new CommunityPostDTO(post);
    }

    //모든 게시글 조회
    @Transactional
    public List<CommunityPostDTO> getAllPosts() {
        List<CommunityPost> posts = postRepository.findAll();
        return posts.stream().map(CommunityPostDTO::new).collect(Collectors.toList());
    }

    //좋아요 카운트 변경
    @Transactional
    public CommunityPost updateLikeCount(Long id, boolean isLiked) {
        CommunityPost post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (isLiked) {
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1)); // 음수 방지
        }

        return postRepository.save(post);
    }

}
