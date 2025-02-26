package com.example.seollyongbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "post_id")
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickName;// 게시물 작성자 닉네임

    @Column(nullable = false)
    private String userDistrict; // 게시물 작성자의 지역 (User의 town)

    @Column(nullable = false)
    private String postDistrict; // 게시물이 작성된 커뮤니티 지역

    @Column(nullable = false)
    private Boolean isResident; // 거주자 인증 여부

    @Column(nullable = false)
    private String title; // 게시물 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 게시물 내용

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 게시글 생성 시간

    @Column(nullable = false)
    private int likeCount = 0; // 좋아요 수 (기본값 0)

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount = 0; // 조회 수 (기본값 0)

    @Column(nullable = false)
    private int commentCount = 0; // 댓글 수 (기본값 0)

    //기본 생성자
    public CommunityPost() {}

    //파라미터 있는 생성자 -> 새 게시글을 생성할 때 사용
    public CommunityPost(String nickName, String userDistrict, String postDistrict, Boolean isResident,
                         String title, String content) {
        this.nickName = nickName;
        this.userDistrict = userDistrict;
        this.postDistrict = postDistrict;
        this.isResident = isResident;
        this.title = title;
        this.content = content;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getNickName() { return nickName; }
    public String getUserDistrict() { return userDistrict; }
    public String getPostDistrict() { return postDistrict; }
    public Boolean getIsResident() { return isResident; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getLikeCount() { return likeCount; }
    public int getViewCount() { return viewCount; }
    public int getCommentCount() { return commentCount; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }
}
