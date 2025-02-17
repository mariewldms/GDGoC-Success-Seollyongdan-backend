package com.example.seollyongbackend.repository;

import com.example.seollyongbackend.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Long> {
}
