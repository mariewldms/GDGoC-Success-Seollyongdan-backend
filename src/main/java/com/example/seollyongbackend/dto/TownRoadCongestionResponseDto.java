package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownRoadCongestionResponseDto {
    private String roadCongestion;

    public TownRoadCongestionResponseDto(Town town) {
        this.roadCongestion = town.getRoadCongestion();
    }
}
