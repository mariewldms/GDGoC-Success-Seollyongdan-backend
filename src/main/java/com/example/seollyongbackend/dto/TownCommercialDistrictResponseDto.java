package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownCommercialDistrictResponseDto {
    private Long townId;

    private String top1Commercial;
    private String top2Commercial;
    private String top3Commercial;
    private String top4Commercial;
    private String top5Commercial;

    private Integer top1Count;
    private Integer top2Count;
    private Integer top3Count;
    private Integer top4Count;
    private Integer top5Count;

    private boolean isCulturalArea;

    public TownCommercialDistrictResponseDto(Town town) {
        this.townId = town.getTownId();
        this.top1Commercial = town.getTop1Commercial();
        this.top2Commercial = town.getTop2Commercial();
        this.top3Commercial = town.getTop3Commercial();
        this.top4Commercial = town.getTop4Commercial();
        this.top5Commercial = town.getTop5Commercial();
        this.top1Count = town.getTop1Count();
        this.top2Count = town.getTop2Count();
        this.top3Count = town.getTop3Count();
        this.top4Count = town.getTop4Count();
        this.top5Count = town.getTop5Count();

        this.isCulturalArea = town.getFacilityRank() <= 5;
    }
}
