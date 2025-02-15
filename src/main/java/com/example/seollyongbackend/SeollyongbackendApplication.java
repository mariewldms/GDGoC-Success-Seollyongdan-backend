package com.example.seollyongbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//배치 사용
//@EnableBatchProcessing
@SpringBootApplication()
@EnableScheduling
public class SeollyongbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeollyongbackendApplication.class, args);
	}

}
