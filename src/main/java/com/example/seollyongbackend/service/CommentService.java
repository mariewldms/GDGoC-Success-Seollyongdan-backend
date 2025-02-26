package com.example.seollyongbackend.service;

import com.example.seollyongbackend.dto.CommentRequestDto;
import com.example.seollyongbackend.dto.CommentResponseDto;
import com.example.seollyongbackend.entity.Comment;
import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.entity.User;
import com.example.seollyongbackend.repository.CommentRepository;
import com.example.seollyongbackend.repository.CommunityPostRepository;
import com.example.seollyongbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto) {
        System.out.println("🔍 조회할 postId: " + postId);
        CommunityPost communityPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        System.out.println("✅ 게시글 찾음: " + communityPost.getTitle());

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .communityPost(communityPost)
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        );
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        comment.updateContent(requestDto.getContent());

        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        );
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByCommunityPost_Id(postId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getCreatedDate(),
                        comment.getModifiedDate()))
                .collect(Collectors.toList());
    }
}

