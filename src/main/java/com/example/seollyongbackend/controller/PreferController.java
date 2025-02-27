package com.example.seollyongbackend.controller;

import com.example.seollyongbackend.dto.ApiResponse;
import com.example.seollyongbackend.dto.GetPreferenceDto;
import com.example.seollyongbackend.entity.Cluster;
import com.example.seollyongbackend.service.PreferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class PreferController {
    private PreferService preferService;

    public PreferController(PreferService preferService) {
        this.preferService = preferService;
    }

    @PostMapping("/preference")
    public ResponseEntity<ApiResponse<Optional<Cluster>>> postPreference(@RequestBody GetPreferenceDto getPreferenceDto){
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, preferService.findCluster(getPreferenceDto)));
    }
    //post 받아서 네 가지 요소와 일치하는 clustering 번호 알아내고
    //cluster 테이블의 id와 일치하는 값 response로 보내기

    @PutMapping("/preference")
    public ResponseEntity<ApiResponse<Optional<Cluster>>> putPreference(@RequestBody GetPreferenceDto getPreferenceDto){
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, preferService.findCluster(getPreferenceDto)));
    }
    //post와 동일한 흐름



}
