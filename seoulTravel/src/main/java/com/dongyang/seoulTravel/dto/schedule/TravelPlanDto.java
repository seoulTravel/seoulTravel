package com.dongyang.seoulTravel.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TravelPlanDto {
    private String planName;
    private String startDate;
    private String endDate;
    private List<TravelPlanItemDto> items;
    private List<AccommodationPeriodDto> accommodations; // 숙소 정보와 기간 추가
}
