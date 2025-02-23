package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    List<Town> findAll(); // 전체 자치구 조회
    List<Town> findTop5ByOrderBySafetyRankAsc();
    // 특정 자치구의 대중교통 이용 정보 조회
    Optional<Town> findByTownId(Long townId);
    List<Town> findTop5ByOrderByCongestionRankAsc();
    List<Town> findTop5ByOrderByFacilityRankAsc();
    boolean existsByTownName(String townName);
}
