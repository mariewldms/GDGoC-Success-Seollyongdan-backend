package com.example.seollyongdanbackend.service;

import com.example.seollyongdanbackend.dto.TownPropertiesResponseDto;
import com.example.seollyongdanbackend.dto.TownSafetyResponseDto;
import com.example.seollyongdanbackend.dto.TownCrimeFrequencyResponseDto;
import com.example.seollyongdanbackend.entity.Town;
import com.example.seollyongdanbackend.repository.TownRepository;
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
}

