package com.example.seollyongbackend.dto;


import com.example.seollyongbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownCrimeFrequencyResponseDto {
    private String crimeFrequency;

    public TownCrimeFrequencyResponseDto(Town town) {
        this.crimeFrequency = town.getCrimeFrequency();
    }
}
