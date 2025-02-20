package com.example.seollyongbackend.scheduler;

import com.example.seollyongbackend.service.CsvBatchService;
import com.example.seollyongbackend.service.CsvClusterBatchService;
import com.example.seollyongbackend.service.CsvTownBatchService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvBatchScheduler {

    private final CsvBatchService csvBatchService;
    private final CsvClusterBatchService csvClusterBatchService;
    private final CsvTownBatchService   csvTownBatchService;

    public CsvBatchScheduler(CsvBatchService csvBatchService, CsvClusterBatchService csvClusterBatchService, CsvTownBatchService csvTownBatchService) {
        this.csvBatchService = csvBatchService;
        this.csvClusterBatchService = csvClusterBatchService;
        this.csvTownBatchService = csvTownBatchService;
    }

    @PostConstruct
    public void runCsvBatch() {
        //System.out.println("CSV 배치 실행 중...");
        csvBatchService.processCsv();
        csvClusterBatchService.processCsv();
        csvTownBatchService.processCsv();
    }


}
