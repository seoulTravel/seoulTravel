package com.dongyang.seoulTravel.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "travel_plan_item")
public class TravelPlanItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String placeType;
    private String placeId;

    @ManyToOne
    @JoinColumn(name = "travel_plan_id")
    private TravelPlanEntity travelPlan;
}
