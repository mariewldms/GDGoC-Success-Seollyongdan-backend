package com.example.seollyongdanbackend.controller;


import com.example.seollyongdanbackend.dto.TownSafetyResponseDto;
import com.example.seollyongdanbackend.service.TownService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/town")
public class TownController {
    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping("/{town-id}/safety")
    public TownSafetyResponseDto getTownSafety(@PathVariable("town-id") Long townId) {
        return townService.getSafetyInfo(townId);
    }
}
