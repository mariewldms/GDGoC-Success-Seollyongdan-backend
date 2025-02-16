package com.example.seollyongbackend.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="cluster")
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String cluster_id;

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

    public Cluster(String cluster_id, String safety, String traffic, String real_estate, String amenities, String description, String town, String animal, String animal_description) {
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

    // JSON 응답에서 `town`을 List<String> 형태로 변환하여 반환
    @JsonGetter("town")
    public List<String> getTownAsList() {
        return Arrays.asList(town.split(" ")); // 공백(" ") 기준으로 리스트 변환
    }

    @JsonGetter("animal_description")
    public List<String> getAnimalDescriptionAsList() {
        return Arrays.asList(animal_description.split("/"));
    }



    // Getters & Setters
    public String getCluster_id() { return cluster_id; }
    public String getSafety() { return safety; }
    public String getTraffic() { return traffic; }
    public String getReal_estate() { return real_estate; }
    public String getAmenities() { return amenities; }
    public String getDescription() { return description; }
    public String getTown() { return town; }
    public String getAnimal() { return animal; }
    public String getAnimal_description() { return animal_description; }

    public void setCluster_id(String cluster_id) { this.cluster_id = cluster_id; }
    public void setSafety(String safety) { this.safety = safety; }
    public void setTraffic(String traffic) { this.traffic = traffic; }
    public void setReal_estate(String real_estate) {this.real_estate = real_estate; }
    public void setAmenities(String amenities) {this.amenities = amenities; }
    public void setDescription(String description) { this.description = description; }
    public void setTown(String town) { this.town = town; }
    public void setAnimal(String animal) { this.animal = animal; }
    public void setAnimal_description(String animal_description) { this.animal_description = animal_description; }
}
