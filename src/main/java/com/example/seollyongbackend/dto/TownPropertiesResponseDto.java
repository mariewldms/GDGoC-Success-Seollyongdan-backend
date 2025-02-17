package com.example.seollyongbackend.dto;

import com.example.seollyongdanbackend.entity.Town;
import lombok.Getter;

@Getter
public class TownPropertiesResponseDto {
    private Long townId;
    private String townName;

    private Float monthlyRent;
    private Float yearlyRent;

    private Float[] saleData;


    private Float priceDifference1y;

    public TownPropertiesResponseDto(Town town) {
        this.townId = town.getTownId();
        this.townName = town.getTownName();
        this.monthlyRent = town.getMonthlyRent();
        this.yearlyRent = town.getYearlyRent();

        this.saleData = new Float[] {
                town.getSale2401(), town.getSale2402(), town.getSale2403(), town.getSale2404(),
                town.getSale2405(), town.getSale2406(), town.getSale2407(),
                town.getSale2408(), town.getSale2409(), town.getSale2410(),
                town.getSale2411(), town.getSale2412(),
                town.getSale2501()
        };

        this.priceDifference1y = town.getPriceDifference1y();
    }

}
