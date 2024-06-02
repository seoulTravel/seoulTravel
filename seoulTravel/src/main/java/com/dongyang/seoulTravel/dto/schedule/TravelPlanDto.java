package com.dongyang.seoulTravel.dto.schedule;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TravelPlanDto {
    private String planName;
    private String startDate;
    private String endDate;
    private List<TravelPlanItemDto> items;
}
