package com.example.seollyongbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String town;

    @Column
    private boolean resident;


    public User() {}

    public User(String username, String password, String nickname, String town, boolean resident) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.town = town;
        this.resident = resident;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public String getTown() { return town; }
    public boolean isResident() { return resident; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setTown(String town) { this.town = town; }
}