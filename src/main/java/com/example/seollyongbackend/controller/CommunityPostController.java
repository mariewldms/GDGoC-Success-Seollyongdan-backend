package com.example.seollyongbackend.controller;

import com.example.seollyongbackend.dto.CommunityPostDTO;
import com.example.seollyongbackend.entity.CommunityPost;
import com.example.seollyongbackend.service.CommunityPostService;
import com.example.seollyongbackend.dto.CommunityPostRequest;
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

    public CommunityPostController(CommunityPostService postService) {
        this.postService = postService;
    }

    //게시글 작성
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommunityPost> createPost(@RequestBody CommunityPostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    //게시글 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommunityPost> updatePost(@PathVariable Long id, @RequestBody CommunityPostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    //게시물 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);

        // ✅ JSON 응답을 위한 Map 생성
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value()); // 🔥 OK 상태 코드를 숫자로 반환
        response.put("success", true);
        response.put("result", "게시글이 삭제되었습니다.");
        response.put("error", null);

        return ResponseEntity.ok(response);
    }

    //특정 게시글 조회 : 특정 id의 게시글을 가져오는 API
    @GetMapping("/{id}")
    public ResponseEntity<CommunityPostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //게시물 리스트 조회
    @GetMapping
    public ResponseEntity<List<CommunityPostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
