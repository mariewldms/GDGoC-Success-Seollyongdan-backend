package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownTransportResponseDto {
    private Long townId;
    private Float busRatio;
    private Float subwayRatio;
    private Float taxiRatio;
    private String mostUsedTransport;

    private boolean isHighCongestion;

    public TownTransportResponseDto(Town town) {
        this.townId = town.getTownId();
        this.busRatio = town.getBusRatio();
        this.subwayRatio = town.getSubwayRatio();
        this.taxiRatio = town.getTaxiRatio();
        this.mostUsedTransport = town.getMostUsedTransport();
        this.isHighCongestion = town.getCongestionRank() <= 5;
    }
}
