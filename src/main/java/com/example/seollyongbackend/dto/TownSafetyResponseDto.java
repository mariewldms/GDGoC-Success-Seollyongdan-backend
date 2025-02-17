package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownSafetyResponseDto {
    private Long townId;
    private String townName;

    private Integer[] crimeData;
    private String crimeFrequency;

    private Integer cctvCount;
    private Integer policeStations;
    private Integer fireStations;


    public TownSafetyResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();

        this.crimeData = new Integer[]{
                town.getCrime2014(), town.getCrime2015(), town.getCrime2016(),
                town.getCrime2017(), town.getCrime2018(), town.getCrime2019(),
                town.getCrime2020(), town.getCrime2021(), town.getCrime2022(),
                town.getCrime2023()
        };
        this.crimeFrequency = town.getCrimeFrequency();

        this.cctvCount = town.getCctvCount();
        this.policeStations = town.getPoliceStations();
        this.fireStations = town.getFireStations();
    }
}
