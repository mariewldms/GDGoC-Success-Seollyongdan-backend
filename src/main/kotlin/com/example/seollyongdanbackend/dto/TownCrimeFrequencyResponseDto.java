package com.example.seollyongdanbackend.dto;


import com.example.seollyongdanbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownCrimeFrequencyResponseDto {
    private Long townId;
    private String townName;
    private String crimeFrequency;

    public TownCrimeFrequencyResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.crimeFrequency = town.getCrimeFrequency();
    }
}
