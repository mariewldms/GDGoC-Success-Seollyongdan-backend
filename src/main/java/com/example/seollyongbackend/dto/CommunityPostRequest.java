package com.example.seollyongbackend.dto;

public class CommunityPostRequest {
    private String nickName;
    private String postDistrict;
    private String title;
    private String content;

    public CommunityPostRequest() {}

    public CommunityPostRequest(String userName, String postDistrict, String title, String content) {
        this.nickName = userName;
        this.postDistrict = postDistrict;
        this.title = title;
        this.content = content;
    }

    public String getNickName() { return nickName; }
    public String getPostDistrict() { return postDistrict; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
