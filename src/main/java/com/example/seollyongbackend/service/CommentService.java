package com.example.seollyongbackend.service;

import com.example.seollyongbackend.dto.ApiResponse;
import com.example.seollyongbackend.dto.CommentRequestDto;
import com.example.seollyongbackend.dto.CommentResponseDto;
import com.example.seollyongbackend.entity.Comment;
import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.entity.User;
import com.example.seollyongbackend.repository.CommentRepository;
import com.example.seollyongbackend.repository.CommunityPostRepository;
import com.example.seollyongbackend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityPostRepository communityPostRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ApiResponse<CommentResponseDto> createComment(Long postId, CommentRequestDto requestDto) {
        System.out.println("🔍 댓글 생성 요청: postId=" + postId + ", content=" + requestDto.getContent());

        CommunityPost communityPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> {
                    System.out.println("❌ 게시글을 찾을 수 없음: postId=" + postId);
                    return new RuntimeException("게시글을 찾을 수 없습니다.");
                });

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .communityPost(communityPost)
                .build();

        commentRepository.save(comment);

        System.out.println("✅ 댓글 저장 완료: commentId=" + comment.getCommentId());

        return ApiResponse.success(HttpStatus.CREATED, new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        ));
    }


    public ApiResponse<CommentResponseDto> updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        comment.updateContent(requestDto.getContent());
        entityManager.flush();
        entityManager.refresh(comment);

        return ApiResponse.success(HttpStatus.OK, new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        ));
    }

    public ApiResponse<Void> deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(comment);
        return ApiResponse.success(HttpStatus.NO_CONTENT, null);
    }

    public ApiResponse<List<CommentResponseDto>> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByCommunityPost_Id(postId);

        List<CommentResponseDto> responseList = comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getCreatedDate(),
                        comment.getModifiedDate()))
                .collect(Collectors.toList());
        return ApiResponse.success(HttpStatus.OK, responseList);
    }
}
