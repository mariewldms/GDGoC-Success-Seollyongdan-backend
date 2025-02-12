package com.example.seollyongdanbackend.dto;

import com.example.seollyongdanbackend.entity.Town;
import lombok.Getter;

@Getter
public class TopCulturalTownsResponseDto {
    private Long townId;
    private String townName;
    private Integer facilityCount;
    private Integer facilityRank;

    public TopCulturalTownsResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.facilityCount = town.getFacilityCount();
        this.facilityRank = town.getFacilityRank();
    }
}
