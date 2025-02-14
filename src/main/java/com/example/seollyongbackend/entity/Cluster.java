package com.example.seollyongbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cluster")
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cluster_id;

//    @Column(nullable=false)
//    private String cluster_id;

    @Column(nullable = false)
    private String safety;

    @Column(nullable = false)
    private String traffic;

    @Column(nullable = false)
    private String real_estate;

    @Column(nullable = false)
    private String amenities;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String animal;

    @Column(nullable=false)
    private String animal_description;

    public Cluster(){}

    public Cluster(Long cluster_id, String safety, String traffic, String real_estate, String amenities, String description, String town, String animal, String animal_description) {
        this.cluster_id = cluster_id;
        this.safety = safety;
        this.traffic = traffic;
        this.real_estate = real_estate;
        this.amenities = amenities;
        this.description = description;
        this.town = town;
        this.animal = animal;
        this.animal_description = animal_description;

    }

    // Getters & Setters
    public Long getCluster_id() { return cluster_id; }
    public String getSafety() { return safety; }
    public String getTraffic() { return traffic; }
    public String getReal_estate() { return real_estate; }
    public String getAmenities() { return amenities; }
    public String getDescription() { return description; }
    public String getTown() { return town; }
    public String getAnimal() { return animal; }
    public String getAnimal_description() { return animal_description; }

    public void setCluster_id(Long cluster_id) { this.cluster_id = cluster_id; }
    public void setSafety(String safety) { this.safety = safety; }
    public void setTraffic(String traffic) { this.traffic = traffic; }
    public void setReal_estate(String real_estate) {this.real_estate = real_estate; }
    public void setAmenities(String amenities) {this.amenities = amenities; }
    public void setDescription(String description) { this.description = description; }
    public void setTown(String town) { this.town = town; }
    public void setAnimal(String animal) { this.animal = animal; }
    public void setAnimal_description(String animal_description) { this.animal_description = animal_description; }
}
