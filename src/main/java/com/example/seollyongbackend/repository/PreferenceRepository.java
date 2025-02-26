package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    @Query("SELECT p FROM Preference p WHERE p.safety = :safety AND p.traffic = :traffic AND p.realEstate = :realEstate AND p.amenities = :amenities")
    List<Preference> findByExactMatch(
            @Param("safety") String safety,
            @Param("traffic") String traffic,
            @Param("realEstate") String realEstate,
            @Param("amenities") String amenities
    );
//    Optional<Preference> findBySafetyAndTrafficAndRealEstateAndAmenities(
//            String safety, String traffic, String realEstate, String amenities
//    );
}
