package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.dto.schedule.PlaceDto;
import com.dongyang.seoulTravel.service.scheduleService.PlaceApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceApiController {

    private final PlaceApiService placeApiService;

    @Autowired
    public PlaceApiController(PlaceApiService placeApiService) {
        this.placeApiService = placeApiService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAllPlaces() {
        try {
            List<PlaceDto> places = placeApiService.getAllPlaces();

            return ResponseEntity.ok(places);
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null);
        }
    }
}

