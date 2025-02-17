package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownTransportResponseDto {
    private Long townId;
    private String townName;
    private Float busRatio;
    private Float subwayRatio;
    private Float taxiRatio;
    private String mostUsedTransport;

    public TownTransportResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.busRatio = town.getBusRatio();
        this.subwayRatio = town.getSubwayRatio();
        this.taxiRatio = town.getTaxiRatio();
        this.mostUsedTransport = town.getMostUsedTransport();
    }
}
