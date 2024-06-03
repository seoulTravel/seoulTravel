package com.dongyang.seoulTravel.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TravelPlanItemDto {
    private String date;
    private String placeType;
    private String placeId;
}
