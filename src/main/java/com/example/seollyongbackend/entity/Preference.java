package com.example.seollyongbackend.entity;
import jakarta.persistence.*;

@Entity
@Table(name="preference")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prefer_id;

    //상관없음:1. 중요:2, 매우중요:3
    @Column(nullable = false)
    private String safety;

    @Column(nullable = false)
    private String traffic;

    @Column(nullable = false)
    private String real_estate;

    @Column(nullable = false)
    private String amenities;

    @Column(nullable = false)
    private String clustering;

    public Preference(){}

    public Preference(String safety, String traffic, String real_estate, String amenities, String clustering) {
        this.safety = safety;
        this.traffic = traffic;
        this.real_estate = real_estate;
        this.amenities = amenities;
        this.clustering = clustering;
    }

}
