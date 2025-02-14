package com.example.seollyongbackend.service;

import com.example.seollyongbackend.entity.Cluster;
import com.example.seollyongbackend.entity.Preference;
import com.example.seollyongbackend.repository.ClusterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvClusterBatchService {
    private final ClusterRepository clusterRepository;

    public CsvClusterBatchService(ClusterRepository clusterRepository) {
        this.clusterRepository=clusterRepository;
    }

    @Transactional
    public void processCsv(){
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cluster.csv")){
            if (is==null) throw new FileNotFoundException("CSV 파일을 찾을 수 없습니다");
            //csv 파일 읽어오기
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            List<Cluster> dataList=new ArrayList<>();
            String line;

            boolean firstLine=true;
            while ((line=br.readLine())!=null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                //csv 파일 쉼표 기준으로 나누기
                String[] fields = line.split(",");

                Cluster cluster=new Cluster();
                //cluster.setCluster_id(Long.parseLong(fields[1].trim())+1);
                cluster.setSafety(fields[2].trim());
                cluster.setTraffic(fields[3].trim());
                cluster.setReal_estate(fields[4].trim());
                cluster.setAmenities(fields[5].trim());
                cluster.setDescription(fields[6].trim());
                cluster.setTown(fields[7].trim());
                cluster.setAnimal(fields[8].trim());
                cluster.setAnimal_description(fields[9].trim());

                dataList.add(cluster);
            }
            clusterRepository.saveAll(dataList);
            System.out.println("CSV 파일 데이터 저장 완료");

        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }

    }
}
