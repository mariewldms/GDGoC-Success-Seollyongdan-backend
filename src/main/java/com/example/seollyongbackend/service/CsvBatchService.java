package com.example.seollyongbackend.service;

import com.example.seollyongbackend.entity.Preference;
import com.example.seollyongbackend.repository.PreferenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.example.seollyongbackend.repository.PreferenceRepository;

@Service
//@RequiredArgsConstructor
public class CsvBatchService {
    private final PreferenceRepository preferenceRepository;

    public CsvBatchService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Transactional
    public void processCsv(){
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("filtering.csv")){
            if (is==null) throw new FileNotFoundException("CSV 파일을 찾을 수 없습니다");
            //csv 파일 읽어오기
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            List<Preference> dataList=new ArrayList<>();
            String line;

            boolean firstLine=true;
            while ((line=br.readLine())!=null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                //csv 파일 쉼표 기준으로 나누기
                String[] fields = line.split(",");

                Preference preference = new Preference();
                preference.setSafety(fields[1].trim());
                preference.setTraffic(fields[2].trim());
                preference.setReal_estate(fields[3].trim());
                preference.setAmenities(fields[4].trim());
                preference.setClustering(fields[5].trim());

                dataList.add(preference);
            }
            preferenceRepository.saveAll(dataList);
            System.out.println("CSV 파일 데이터 저장 완료");

        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }
    }




}
