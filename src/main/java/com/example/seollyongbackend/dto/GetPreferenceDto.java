package com.example.seollyongbackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.Column;

public class GetPreferenceDto {
    private String safety;
    private String traffic;
    private String real_estate;
    private String amenities;

    public GetPreferenceDto(String safety, String traffic, String real_estate, String amenities) {
        this.safety = safety;
        this.traffic = traffic;
        this.real_estate = real_estate;
        this.amenities = amenities;
    }

    public String getSafety(){ return safety; }
    public String getTraffic(){ return traffic; }
    public String getReal_estate(){ return real_estate;}
    public String getAmenities(){ return amenities;}

}
