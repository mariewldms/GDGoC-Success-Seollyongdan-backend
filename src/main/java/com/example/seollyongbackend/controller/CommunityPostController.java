package com.example.seollyongbackend.controller;

import com.example.seollyongbackend.dto.ApiResponse;
import com.example.seollyongbackend.dto.CommunityPostDTO;
import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.service.CommunityPostService;
import com.example.seollyongbackend.dto.CommunityPostRequest;
import com.example.seollyongbackend.service.PostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//HTTP 요청을 처리하는 REST API 컨트롤러
@RestController
@RequestMapping("/posts")
public class CommunityPostController {

    private final CommunityPostService postService;
    private final PostLikeService postLikeService;

    public CommunityPostController(CommunityPostService postService, PostLikeService postLikeService) {
        this.postService = postService;
        this.postLikeService = postLikeService;
    }

    //게시글 작성
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CommunityPost>> createPost(@RequestBody CommunityPostRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postService.createPost(request)));
    }

    //게시글 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CommunityPost>> updatePost(@PathVariable Long id, @RequestBody CommunityPostRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postService.updatePost(id, request)));
    }

    //게시물 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "게시글이 삭제되었습니다."));
    }

    //특정 게시글 조회 : 특정 id의 게시글을 가져오는 API
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommunityPostDTO>> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postService.getPostById(id)));
    }

    //게시물 리스트 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommunityPostDTO>>> getAllPosts() {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postService.getAllPosts()));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<String>> likePost(@RequestParam Long userId, @PathVariable Long postId) {
        postLikeService.likePost(userId, postId);
        //해당 postId의 like_Count값을 +1하기
        postLikeService.plusLikeCount(postId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Liked post successfully"));
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<ApiResponse<String>> unlikePost(@RequestParam Long userId, @PathVariable Long postId) {
        postLikeService.unlikePost(userId, postId);
        postLikeService.minusLikeCount(postId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Unliked post successfully"));
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<Long>> getLikeCount(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postLikeService.getLikeCount(postId)));
    }

    //keyword를 param으로 받아서 해당 param과 일치하는 게시글 내용이나 제목이 있는지 쿼리로 확인하기
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CommunityPostDTO>>> postSearch(@RequestParam(value="district") String district, @RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postService.searchPosts(district, keyword)));
    }


    // ✅ 특정 동네의 게시글만 조회하는 API 추가
    @GetMapping("/district/{postDistrict}")
    public ResponseEntity<ApiResponse<List<CommunityPostDTO>>> getPostsByDistrict(@PathVariable String postDistrict) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, postService.getPostsByDistrict(postDistrict)));
    }
}
