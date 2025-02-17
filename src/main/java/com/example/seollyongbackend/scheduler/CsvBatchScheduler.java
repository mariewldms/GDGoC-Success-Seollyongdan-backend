package com.example.seollyongbackend.scheduler;

import com.example.seollyongbackend.service.CsvBatchService;
import com.example.seollyongbackend.service.CsvClusterBatchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvBatchScheduler {

    private final CsvBatchService csvBatchService;
    private final CsvClusterBatchService csvClusterBatchService;

    public CsvBatchScheduler(CsvBatchService csvBatchService, CsvClusterBatchService csvClusterBatchService) {
        this.csvBatchService = csvBatchService;
        this.csvClusterBatchService = csvClusterBatchService;
    }

    @Scheduled(fixedRate = 36000000) // Config에서 값 가져오기
    public void runCsvBatch() {
        //System.out.println("CSV 배치 실행 중...");
        csvBatchService.processCsv();
        csvClusterBatchService.processCsv();
    }


}
