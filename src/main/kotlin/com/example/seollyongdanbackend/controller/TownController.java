package com.example.seollyongdanbackend.controller;


import com.example.seollyongdanbackend.dto.TownCrimeFrequencyResponseDto;
import com.example.seollyongdanbackend.dto.TownPropertiesResponseDto;
import com.example.seollyongdanbackend.dto.TownSafetyResponseDto;
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

    @GetMapping("/{town-id}/properties")
    public TownPropertiesResponseDto getTownProperties(@PathVariable("town-id") Long townId) {
        return townService.getPropertiesInfo(townId);
    }


}
