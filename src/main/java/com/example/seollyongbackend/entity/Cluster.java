package com.example.seollyongbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cluster")
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cluster_id;

    @Column(nullable = false)
    private String safety;

    @Column(nullable = false)
    private String traffic;

    @Column(nullable = false)
    private String real_estate;

    @Column(nullable = false)
    private String amenities;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String animal;

    public Cluster(){}

    public Cluster(String safety, String traffic, String real_estate, String amenities, String town, String animal) {
        this.safety = safety;
        this.traffic = traffic;
        this.real_estate = real_estate;
        this.amenities = amenities;
        this.town = town;
        this.animal = animal;
    }
}
