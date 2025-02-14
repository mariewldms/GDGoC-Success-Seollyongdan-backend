package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    //findBy 이름 규칙 지키기
    Optional<Preference> findBySafetyAndTrafficAndRealEstateAndAmenities(
            String safety, String traffic, String realEstate, String amenities
    );
}
