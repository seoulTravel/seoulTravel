package com.dongyang.seoulTravel.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TravelPlanItemDto {
    private LocalDate date;
    private String placeType;
    private Long placeId;
}
