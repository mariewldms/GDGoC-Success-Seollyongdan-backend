package com.example.seollyongdanbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "town")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long townId;  // 자치구 ID

    @Column(nullable = false, length = 50)
    private String townName;  // 자치구 이름

    // 범죄 발생 건수
    private Integer crime2014;
    private Integer crime2015;
    private Integer crime2016;
    private Integer crime2017;
    private Integer crime2018;
    private Integer crime2019;
    private Integer crime2020;
    private Integer crime2021;
    private Integer crime2022;
    private Integer crime2023;

    @Column(length = 20)
    private String crimeFrequency; // 범죄 발생 빈도

    // 안전 관련 정보
    private Integer cctvCount;
    private Integer policeStations;
    private Integer fireStations;
    private Float safetyScore;
    private Integer safetyRank;

    // 부동산 정보
    private Float monthlyRent;
    private Float yearlyRent;

    private Float sale2401;
    private Float sale2402;
    private Float sale2403;
    private Float sale2404;
    private Float sale2405;
    private Float sale2406;
    private Float sale2407;
    private Float sale2408;
    private Float sale2409;
    private Float sale2410;
    private Float sale2411;
    private Float sale2412;
    private Float sale2501;
    private Float priceDifference1y;

    // 교통 정보
    private Float busRatio;
    private Float subwayRatio;
    private Float taxiRatio;

    @Column(length = 50)
    private String mostUsedTransport;

    private Integer congestionScore;

    @Column(length = 20)
    private String roadCongestion;

    private Integer congestionRank;

    // 상업 시설 정보
    @Column(length = 50)
    private String top1Commercial;

    @Column(length = 50)
    private String top2Commercial;

    @Column(length = 50)
    private String top3Commercial;

    @Column(length = 50)
    private String top4Commercial;

    @Column(length = 50)
    private String top5Commercial;

    private Integer top1Count;
    private Integer top2Count;
    private Integer top3Count;
    private Integer top4Count;
    private Integer top5Count;

    // 문화 시설 정보
    private Integer facilityCount;
    private Integer facilityRank;
}
