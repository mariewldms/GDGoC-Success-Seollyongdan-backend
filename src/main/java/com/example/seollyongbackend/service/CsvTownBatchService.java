package com.example.seollyongbackend.service;

import com.example.seollyongbackend.entity.Town;
import com.example.seollyongbackend.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvTownBatchService {
    private final TownRepository townRepository;

    public CsvTownBatchService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Transactional
    public void processCsv() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("town_dic.CSV")) {
            if (is == null) throw new FileNotFoundException("CSV 파일을 찾을 수 없습니다");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            List<Town> townList = new ArrayList<>();
            String line;

            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] fields = line.split(",");

                Town town = new Town();
                town.setTownName(fields[1].trim());
                town.setCrime2014(Integer.parseInt(fields[2].trim()));
                town.setCrime2015(Integer.parseInt(fields[3].trim()));
                town.setCrime2016(Integer.parseInt(fields[4].trim()));
                town.setCrime2017(Integer.parseInt(fields[5].trim()));
                town.setCrime2018(Integer.parseInt(fields[6].trim()));
                town.setCrime2019(Integer.parseInt(fields[7].trim()));
                town.setCrime2020(Integer.parseInt(fields[8].trim()));
                town.setCrime2021(Integer.parseInt(fields[9].trim()));
                town.setCrime2022(Integer.parseInt(fields[10].trim()));
                town.setCrime2023(Integer.parseInt(fields[11].trim()));
                town.setCrimeFrequency(fields[12].trim());
                town.setCctvCount(Integer.parseInt(fields[13].trim()));
                town.setPoliceStations(Integer.parseInt(fields[14].trim()));
                town.setFireStations(Integer.parseInt(fields[15].trim()));
                town.setSafetyScore(Float.parseFloat(fields[16].trim()));
                town.setSafetyRank(Integer.parseInt(fields[17].trim()));
                town.setMonthlyRent(Float.parseFloat(fields[18].trim()));
                town.setYearlyRent(Float.parseFloat(fields[19].trim()));
                town.setSale2401(Float.parseFloat(fields[20].trim()));
                town.setSale2402(Float.parseFloat(fields[21].trim()));
                town.setSale2403(Float.parseFloat(fields[22].trim()));
                town.setSale2404(Float.parseFloat(fields[23].trim()));
                town.setSale2405(Float.parseFloat(fields[24].trim()));
                town.setSale2406(Float.parseFloat(fields[25].trim()));
                town.setSale2407(Float.parseFloat(fields[26].trim()));
                town.setSale2408(Float.parseFloat(fields[27].trim()));
                town.setSale2409(Float.parseFloat(fields[28].trim()));
                town.setSale2410(Float.parseFloat(fields[29].trim()));
                town.setSale2411(Float.parseFloat(fields[30].trim()));
                town.setSale2412(Float.parseFloat(fields[31].trim()));
                town.setSale2501(Float.parseFloat(fields[32].trim()));
                town.setPriceDifference1y(Float.parseFloat(fields[33].trim()));
                town.setBusRatio(Float.parseFloat(fields[34].trim()));
                town.setSubwayRatio(Float.parseFloat(fields[35].trim()));
                town.setTaxiRatio(Float.parseFloat(fields[36].trim()));
                town.setMostUsedTransport(fields[37].trim());
                town.setCongestionScore(Integer.parseInt(fields[38].trim()));
                town.setRoadCongestion(fields[39].trim());
                town.setCongestionRank(Integer.parseInt(fields[40].trim()));
                town.setTop1Commercial(fields[41].trim());
                town.setTop2Commercial(fields[42].trim());
                town.setTop3Commercial(fields[43].trim());
                town.setTop4Commercial(fields[44].trim());
                town.setTop5Commercial(fields[45].trim());
                town.setTop1Count(Integer.parseInt(fields[46].trim()));
                town.setTop2Count(Integer.parseInt(fields[47].trim()));
                town.setTop3Count(Integer.parseInt(fields[48].trim()));
                town.setTop4Count(Integer.parseInt(fields[49].trim()));
                town.setTop5Count(Integer.parseInt(fields[50].trim()));
                town.setFacilityCount(Integer.parseInt(fields[51].trim()));
                town.setFacilityRank(Integer.parseInt(fields[52].trim()));

                townList.add(town);
            }

            townRepository.saveAll(townList);
            System.out.println("CSV 파일 데이터 저장 완료");

        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }
    }
}
