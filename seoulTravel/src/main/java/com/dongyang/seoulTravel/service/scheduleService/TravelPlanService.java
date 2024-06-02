package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.TravelPlanDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TravelPlanService {
    void createTravelPlan(TravelPlanDto travelPlanDto); // 여행 일정 생성
    List<TravelPlanDto> getAllTravelPlans(); // 모든 여행 일정 조회
    TravelPlanDto getTravelPlanById(Long id); // 특정 여행 일정 조회
    void updateTravelPlan(Long id, TravelPlanDto travelPlanDto); // 여행 일정 수정
    void deleteTravelPlan(Long id); // 여행 일정 삭제
}

