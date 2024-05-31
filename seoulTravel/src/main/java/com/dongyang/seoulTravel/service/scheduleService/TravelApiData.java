package com.dongyang.seoulTravel.service.scheduleService;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TravelApiData {
    // 여행 일자 생성 -----> 프앤에서 값을 받아서 백엔에서 처리 (C->S)
    // 서비스..? 파일인지는 잘 모르겠음...나중에 파일 따로 생성해서 같이 보관하든가 고민!!!

    // 시작 날짜와 종료 날짜 -----------> 해당 기간 동안의 API 데이터를 생성 후 반환 ,,,
    public static Map<String, String> getApiData(LocalDate startDate, LocalDate endDate) {
        Map<String, String> apiData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 

        LocalDate currentDate = startDate; // 현재 처리 중인 날짜를 시작 날짜로 초기화
        int dayCounter = 1; // 여행 일자 카운터

        // 현재 날짜가 종료 날짜를 넘지 않을 때까지 반복
        while (!currentDate.isAfter(endDate)) {
            String formattedDate = currentDate.format(formatter); // 현재 날짜를 지정된 형식으로 포맷
            // API 데이터 생성 및 맵에 저장
            apiData.put("api" + dayCounter, "API " + dayCounter + " for " + formattedDate);
            currentDate = currentDate.plusDays(1); // 다음 날짜로 이동
            dayCounter++;
        }

        return apiData;
    }
}

