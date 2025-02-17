package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownSafetyRankResponseDto {
    private Long townId;
    private String townName;
    private Integer safetyRank;
    private Float safetyScore;


    public TownSafetyRankResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.safetyRank = town.getSafetyRank();
        this.safetyScore = town.getSafetyScore();
    }
}

