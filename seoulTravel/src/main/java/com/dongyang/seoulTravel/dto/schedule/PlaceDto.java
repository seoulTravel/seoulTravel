package com.dongyang.seoulTravel.dto.schedule;

public interface PlaceDto {

    // 전체 조회용
    String getName();

    // 최단거리 알고리즘용
    String getLatitude();
    String getLongitude();
}
