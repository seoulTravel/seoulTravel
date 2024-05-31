package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.PlaceDto;
import java.util.List;
import com.dongyang.seoulTravel.dto.schedule.AccommodationDto;
import com.dongyang.seoulTravel.dto.schedule.RestaurantDto;
import com.dongyang.seoulTravel.dto.schedule.SpotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaceApiService {

    private final RestaurantApiService restaurantApiService;
    private final AccommodationApiService accommodationApiService;
    private final SpotApiService spotApiService;

    @Autowired
    public PlaceApiService(RestaurantApiService restaurantApiService,
                        AccommodationApiService accommodationApiService,
                        SpotApiService spotApiService) {
        this.restaurantApiService = restaurantApiService;
        this.accommodationApiService = accommodationApiService;
        this.spotApiService = spotApiService;
    }

    public List<PlaceDto> getAllPlaces() {
        List<PlaceDto> places = new ArrayList<>();

        // 레스토랑 데이터 가져오기
        try {
            List<RestaurantDto> restaurants = restaurantApiService.getAllRestaurant();
            places.addAll(restaurants);
        } catch (Exception e) {

            e.printStackTrace();
        }

        // 숙소 데이터 가져오기
        try {
            List<AccommodationDto> accommodations = accommodationApiService.getAllAccommodation();
            places.addAll(accommodations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 관광지 데이터 가져오기
        try {
            List<SpotDto> spots = spotApiService.getAllSpots();
            places.addAll(spots);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return places;
    }
}

