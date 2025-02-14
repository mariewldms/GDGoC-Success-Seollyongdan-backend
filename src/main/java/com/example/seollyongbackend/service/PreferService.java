package com.example.seollyongbackend.service;

import com.example.seollyongbackend.dto.GetPreferenceDto;
import com.example.seollyongbackend.entity.Cluster;
import com.example.seollyongbackend.entity.Preference;
import com.example.seollyongbackend.repository.ClusterRepository;
import com.example.seollyongbackend.repository.PreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferService {

    private final ClusterRepository clusterRepository;
    private final PreferenceRepository preferenceRepository;

    public PreferService(ClusterRepository clusterRepository, PreferenceRepository preferenceRepository) {
        this.clusterRepository = clusterRepository;
        this.preferenceRepository = preferenceRepository;
    }

    public Optional<Cluster> getById(Long clusterId){
        return clusterRepository.findById(clusterId);
    }

    //받은 네 개의 dto 값과 일치하는 db를 찾아서 해당 db의 cluster 번호를 알아내고
    //cluster 엔터티에서 cluster 번호에 해당되는 레코드를 리턴하기
    public Optional<Cluster> findCluster(GetPreferenceDto getPreferenceDto){
        Optional<String> clusterId=preferenceRepository.findBySafetyAndTrafficAndRealEstateAndAmenities(
                getPreferenceDto.getSafety(),
                getPreferenceDto.getTraffic(),
                getPreferenceDto.getReal_estate(),
                getPreferenceDto.getAmenities()
        ).map(Preference::getClustering);
        if (clusterId.isEmpty()){
            return Optional.empty();
        }
        Long getId=Long.parseLong(clusterId.get());
        return clusterRepository.findById(getId);
    }

}
