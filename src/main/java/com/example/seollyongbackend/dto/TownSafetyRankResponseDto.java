package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownSafetyRankResponseDto {
    private Long townId;
    private String townName;
    private boolean isSafe;



    public TownSafetyRankResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.isSafe = town.getSafetyRank() <= 5;
    }
}

