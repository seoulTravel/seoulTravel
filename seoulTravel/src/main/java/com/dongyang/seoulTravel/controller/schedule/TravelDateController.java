package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.service.scheduleService.TravelApiData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
public class TravelDateController {
    // TraveApiData 쪽 컨트롤러 ------> 프앤에서 값 받기

    @GetMapping("/api/travel")
    public Map<String, String> getTravelData(@RequestParam String start, @RequestParam String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);

        return TravelApiData.getApiData(startDate, endDate);
    }
}

