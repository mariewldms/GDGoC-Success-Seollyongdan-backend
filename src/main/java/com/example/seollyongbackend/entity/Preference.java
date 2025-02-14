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
    private String realEstate;

    @Column(nullable = false)
    private String amenities;

    @Column(nullable = false)
    private String clustering;

    public Preference(){}

    public Preference(String safety, String traffic, String realEstate, String amenities, String clustering) {
        this.safety = safety;
        this.traffic = traffic;
        this.realEstate = realEstate;
        this.amenities = amenities;
        this.clustering = clustering;
    }

    // Getters & Setters
    public Long getId() { return prefer_id; }
    public String getSafety() { return safety; }
    public String getTraffic() { return traffic; }
    public String getReal_estate() { return realEstate; }
    public String getAmenities() { return amenities; }
    public String getClustering() { return clustering; }

    public void setId(Long id) { this.prefer_id = id; }
    public void setSafety(String safety) { this.safety = safety; }
    public void setTraffic(String traffic) { this.traffic = traffic; }
    public void setReal_estate(String real_estate) {this.realEstate = real_estate; }
    public void setAmenities(String amenities) {this.amenities = amenities; }
    public void setClustering(String clustering) { this.clustering = clustering; }


}
