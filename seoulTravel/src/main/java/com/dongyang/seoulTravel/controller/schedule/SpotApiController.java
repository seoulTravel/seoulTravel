package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.dto.schedule.SpotDto;
import com.dongyang.seoulTravel.service.scheduleService.SpotApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spot")
public class SpotApiController {

    @Autowired
    private SpotApiService spotApiService;

    @GetMapping("/attractions")
    public List<SpotDto> getSeoulAttractions() {
        try {
            return spotApiService.getSpotApiService();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
