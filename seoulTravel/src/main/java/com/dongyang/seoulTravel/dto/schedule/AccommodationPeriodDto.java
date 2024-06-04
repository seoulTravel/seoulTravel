package com.dongyang.seoulTravel.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccommodationPeriodDto {

    // 숙소를 설정할 때 - 숙소 정보와 기간 저장 dto
    private String accommodationId;
    private String startDate;
    private String endDate;

}
