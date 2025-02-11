package com.example.seollyongdanbackend.service;

import com.example.seollyongdanbackend.dto.TownSafetyResponseDto;
import com.example.seollyongdanbackend.entity.Town;
import com.example.seollyongdanbackend.repository.TownRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TownService {
    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Transactional(readOnly = true)
    public TownSafetyResponseDto getSafetyInfo(Long townId) {
        Town town = townRepository.findById(townId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자치구가 존재하지 않습니다. ID: " + townId));

        return new TownSafetyResponseDto(town);
    }
}

