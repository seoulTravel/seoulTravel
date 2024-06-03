package com.dongyang.seoulTravel.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TravelPlanDto {
    private String planName;
    private String startDate;
    private String endDate;
    private List<TravelPlanItemDto> items;
    private String accommodationId; // 일정 짜기 전에 숙소 추가하게 만들기
}
