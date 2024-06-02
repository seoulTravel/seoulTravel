package com.dongyang.seoulTravel.repository.schedule;

import com.dongyang.seoulTravel.entity.schedule.TravelPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanRepository extends JpaRepository<TravelPlanEntity, Long> {
}

