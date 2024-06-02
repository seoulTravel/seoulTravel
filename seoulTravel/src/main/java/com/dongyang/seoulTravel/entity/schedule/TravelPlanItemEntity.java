package com.dongyang.seoulTravel.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class TravelPlanItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_plan_id")
    private TravelPlanEntity travelPlan;

    private LocalDate date;
    private String placeType; // 음식점 명소 숙박 타입?
    private Long placeId;
}
