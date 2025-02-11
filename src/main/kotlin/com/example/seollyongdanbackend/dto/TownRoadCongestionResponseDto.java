package com.example.seollyongdanbackend.dto;

import com.example.seollyongdanbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownRoadCongestionResponseDto {
    private Long townId;
    private String townName;
    private String roadCongestion;

    public TownRoadCongestionResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.roadCongestion = town.getRoadCongestion();
    }
}
