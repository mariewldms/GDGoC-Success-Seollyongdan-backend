package com.example.seollyongbackend.controller;


import com.example.seollyongbackend.dto.*;
import com.example.seollyongbackend.service.TownService;
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
    public List<TownCrimeFrequencyResponseDto> getCrimeFrequencies() {
        return townService.getCrimeFrequencies();
    }

    @GetMapping("/{town-id}/safety")
    public TownSafetyResponseDto getTownSafety(@PathVariable("town-id") Long townId) {
        return townService.getSafetyInfo(townId);
    }

    @GetMapping("/{town-id}/properties")
    public TownPropertiesResponseDto getTownProperties(@PathVariable("town-id") Long townId) {
        return townService.getPropertiesInfo(townId);
    }

    @GetMapping("/congestion")
    public List<TownRoadCongestionResponseDto> getAllRoadCongestion() {
        return townService.getAllRoadCongestion();
    }

    @GetMapping("/{town-id}/transport")
    public TownTransportResponseDto getTransportInfo(@PathVariable("town-id") Long townId) {
        return townService.getTransportInfo(townId);
    }

    @GetMapping("/{town-id}/commercial")
    public TownCommercialDistrictResponseDto getCommercialDistrict(@PathVariable("town-id") Long townId) {
        return townService.getCommercialDistrict(townId);
    }

}
