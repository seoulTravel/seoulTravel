package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.dto.schedule.TravelPlanDto;
import com.dongyang.seoulTravel.service.scheduleService.TravelPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/travelPlans")
public class TravelPlanController {

    @Autowired
    private TravelPlanService travelPlanService;

    @PostMapping
    public void createTravelPlan(@RequestBody TravelPlanDto travelPlanDto) {
        travelPlanService.createTravelPlan(travelPlanDto);
    }

    @GetMapping
    public List<TravelPlanDto> getAllTravelPlans() {
        return travelPlanService.getAllTravelPlans();
    }

    @GetMapping("/{id}")
    public TravelPlanDto getTravelPlanById(@PathVariable Long id) {
        return travelPlanService.getTravelPlanById(id);
    }

    @PutMapping("/{id}")
    public void updateTravelPlan(@PathVariable Long id, @RequestBody TravelPlanDto travelPlanDto) {
        travelPlanService.updateTravelPlan(id, travelPlanDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTravelPlan(@PathVariable Long id) {
        travelPlanService.deleteTravelPlan(id);
    }
}
