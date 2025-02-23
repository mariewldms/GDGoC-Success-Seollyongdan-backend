package com.example.seollyongbackend.service;

import com.example.seollyongbackend.dto.*;
import com.example.seollyongbackend.entity.Town;
import com.example.seollyongbackend.repository.TownRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TownService {
    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public List<TownCrimeFrequencyResponseDto> getCrimeFrequencies() {
        List<Town> towns = townRepository.findAll();
        return towns.stream()
                .map(TownCrimeFrequencyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TownSafetyResponseDto getSafetyInfo(Long townId) {
        Town town = townRepository.findById(townId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자치구가 존재하지 않습니다. ID: " + townId));

        return new TownSafetyResponseDto(town);
    }

    @Transactional(readOnly = true)
    public TownPropertiesResponseDto getPropertiesInfo(Long townId) {
        Town town = townRepository.findById(townId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자치구가 존재하지 않습니다. ID: " + townId));

        return new TownPropertiesResponseDto(town);
    }

    // 전체 자치구의 도로 혼잡도 조회
    public List<TownRoadCongestionResponseDto> getAllRoadCongestion() {
        List<Town> towns = townRepository.findAll();
        return towns.stream()
                .map(TownRoadCongestionResponseDto::new)
                .collect(Collectors.toList());
    }

    public TownTransportResponseDto getTransportInfo(Long townId) {
        Town town = townRepository.findByTownId(townId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자치구가 존재하지 않습니다. ID: " + townId));

        return new TownTransportResponseDto(town);
    }

    // 특정 자치구의 주요 상권 정보 조회
    public TownCommercialDistrictResponseDto getCommercialDistrict(Long townId) {
        Town town = townRepository.findByTownId(townId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자치구가 존재하지 않습니다. ID: " + townId));
        return new TownCommercialDistrictResponseDto(town);
    }
}

