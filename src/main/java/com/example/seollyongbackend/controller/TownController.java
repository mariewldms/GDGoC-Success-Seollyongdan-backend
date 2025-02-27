package com.example.seollyongbackend.controller;


import com.example.seollyongbackend.dto.*;
import com.example.seollyongbackend.service.TownService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/town")
public class TownController {
    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping("/crime-freq")
    public ResponseEntity<ApiResponse<List<TownCrimeFrequencyResponseDto>>> getCrimeFrequencies() {
        return
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, townService.getCrimeFrequencies()));
    }

    @GetMapping("/{town-id}/safety")
    public ResponseEntity<ApiResponse<TownSafetyResponseDto>> getTownSafety(@PathVariable("town-id") Long townId) {
        return
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,townService.getSafetyInfo(townId)));
    }

    @GetMapping("/{town-id}/properties")
    public ResponseEntity<ApiResponse<TownPropertiesResponseDto>> getTownProperties(@PathVariable("town-id") Long townId) {
        return
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,townService.getPropertiesInfo(townId)));
    }

    @GetMapping("/congestion")
    public ResponseEntity<ApiResponse<List<TownRoadCongestionResponseDto>>> getAllRoadCongestion() {
        return
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,townService.getAllRoadCongestion()));
    }

    @GetMapping("/{town-id}/transport")
    public ResponseEntity<ApiResponse<TownTransportResponseDto>> getTransportInfo(@PathVariable("town-id") Long townId) {
        return
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,townService.getTransportInfo(townId)));
    }

    @GetMapping("/{town-id}/commercial")
    public ResponseEntity<ApiResponse<TownCommercialDistrictResponseDto>> getCommercialDistrict(@PathVariable("town-id") Long townId) {
        return
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,townService.getCommercialDistrict(townId)));
    }

}
