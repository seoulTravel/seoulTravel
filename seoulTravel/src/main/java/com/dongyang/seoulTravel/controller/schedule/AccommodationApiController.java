package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.dto.schedule.AccommodationDto;
import com.dongyang.seoulTravel.service.scheduleService.AccommodationApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationApiController {

    private final AccommodationApiService accommodationApiService;

    @Autowired
    public AccommodationApiController(AccommodationApiService accommodationApiService){
        this.accommodationApiService = accommodationApiService;
    }

    // 숙박 리스트 보기
    @GetMapping
    public List<AccommodationDto> getAccommodationList(){
        try{
            return accommodationApiService.getAllAccommodation();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/search")
    public List<AccommodationDto> searchAccommodation(@RequestParam(name = "keyword") String keyword){
        try{
            return accommodationApiService.searchAccommodations(keyword);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
