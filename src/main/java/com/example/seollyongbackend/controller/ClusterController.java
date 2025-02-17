package com.example.seollyongbackend.controller;

import com.example.seollyongbackend.entity.Cluster;
import com.example.seollyongbackend.service.PreferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/town")
public class ClusterController {
    public PreferService preferService;

    public ClusterController(PreferService preferService) {
        this.preferService = preferService;
    }

    //clusterId 보내면 해당 record 내용 담아서 보내기
    @GetMapping("/cluster/{clusterId}")
    public ResponseEntity<?> getCluster(@PathVariable("clusterId") Long clusterId) {
        //DB상에서 1씩 더해진 값으로 저장되어 있음
        Optional<Cluster> record = preferService.getById(clusterId+1);

        return record.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
