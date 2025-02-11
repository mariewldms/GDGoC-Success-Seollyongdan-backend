package com.example.seollyongdanbackend.repository;

import com.example.seollyongdanbackend.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownRepository extends JpaRepository<Town, Long> {
    List<Town> findAll(); // 전체 자치구 조회
    List<Town> findTop5ByOrderBySafetyRankAsc();

}
