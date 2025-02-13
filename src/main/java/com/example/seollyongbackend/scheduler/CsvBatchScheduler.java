package com.example.seollyongbackend.scheduler;

import com.example.seollyongbackend.service.CsvBatchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvBatchScheduler {

    private final CsvBatchService csvBatchService;

    public CsvBatchScheduler(CsvBatchService csvBatchService) {
        this.csvBatchService = csvBatchService;
    }

    @Scheduled(fixedRate = 36000000) // Config에서 값 가져오기
    public void runCsvBatch() {
        System.out.println("CSV 배치 실행 중...");
        csvBatchService.processCsv();
    }
}
