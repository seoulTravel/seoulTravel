package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.dto.schedule.SpotDto;
import com.dongyang.seoulTravel.service.scheduleService.SpotApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spots")
public class SpotApiController {

    private final SpotApiService spotApiService;

    @Autowired
    public SpotApiController(SpotApiService spotApiService) {
        this.spotApiService = spotApiService;
    }

    // 장소 리스트 보기
    @GetMapping
    public List<SpotDto> getSpotList() {
        try {
            return spotApiService.getAllSpots();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 장소 검색
    @GetMapping("/search")
    public List<SpotDto> searchSpots(@RequestParam(name = "keyword") String keyword) {
        try {
//            System.out.println("장소 검색 Controller 디버깅 확인");
            return spotApiService.searchSpots(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
