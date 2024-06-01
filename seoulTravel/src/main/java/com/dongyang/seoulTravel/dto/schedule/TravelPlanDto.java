package com.dongyang.seoulTravel.dto.schedule;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TravelPlanDto {
    private String planName; // 여행 일정 이름
    private String startDate; // 시작 날짜
    private String endDate; // 종료 날짜
    private List<AccommodationDto> accommodations;
    private List<RestaurantDto> restaurants;
    private List<SpotDto> spots;
}
