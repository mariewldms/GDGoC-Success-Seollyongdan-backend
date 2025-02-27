package com.example.seollyongbackend.controller;

import com.example.seollyongbackend.dto.ApiResponse;
import com.example.seollyongbackend.dto.CommentRequestDto;
import com.example.seollyongbackend.dto.CommentResponseDto;
import com.example.seollyongbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.status(201).body(commentService.createComment(postId, requestDto));
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, requestDto));
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getCommentsByPostId(
            @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}



