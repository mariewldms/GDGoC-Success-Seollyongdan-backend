package com.example.seollyongdanbackend.controller;


import com.example.seollyongdanbackend.dto.*;
import com.example.seollyongdanbackend.service.TownService;
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

    @GetMapping("/safety/top5")
    public List<TownSafetyRankResponseDto> getTop5SafeTowns() {
        return townService.getTop5SafeTowns();
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

    @GetMapping("/congestion/top5")
    public List<TopCongestedTownsResponseDto> getTop5CongestedTowns() {
        return townService.getTop5CongestedTowns();
    }

    @GetMapping("/facilities/top5")
    public List<TopCulturalTownsResponseDto> getTop5CulturalTowns() {
        return townService.getTop5CulturalTowns();
    }

    @GetMapping("/{town-id}/commercial")
    public CommercialDistrictResponseDto getCommercialDistrict(@PathVariable("town-id") Long townId) {
        return townService.getCommercialDistrict(townId);
    }

}
