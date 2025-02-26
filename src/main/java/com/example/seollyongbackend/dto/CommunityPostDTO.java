package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.CommunityPost;
import java.time.Duration;
import java.time.LocalDateTime;

public class CommunityPostDTO {
    private Long id;
    private String nickName;
    private String userDistrict;
    private String postDistrict;
    private Boolean isResident;
    private String title;
    private String content;
    private int postTime; // 경과 시간 계산 후 응답
    private int likeCount;
    private int viewCount;
    private int commentCount;

    public CommunityPostDTO(CommunityPost post) {
        this.id = post.getId();
        this.nickName = post.getNickName();
        this.userDistrict = post.getUserDistrict();
        this.postDistrict = post.getPostDistrict();
        this.isResident = post.getIsResident();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
        this.commentCount = post.getCommentCount();

        // 🔥 createdAt을 기준으로 경과 시간(postTime) 계산 (시간 단위)
        this.postTime = (int) Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
    }


    // ✅ 모든 필드에 대해 Getter 추가
    public Long getId() { return id; }
    public String getNickName() { return nickName; }
    public String getUserDistrict() { return userDistrict; }
    public String getPostDistrict() { return postDistrict; }
    public Boolean getIsResident() { return isResident; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getPostTime() { return postTime; }
    public int getLikeCount() { return likeCount; }
    public int getViewCount() { return viewCount; }
    public int getCommentCount() { return commentCount; }
}
