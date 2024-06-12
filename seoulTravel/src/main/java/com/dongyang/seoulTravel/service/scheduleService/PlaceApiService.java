package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.PlaceDto;

import java.util.List;
import com.dongyang.seoulTravel.dto.schedule.AccommodationDto;
import com.dongyang.seoulTravel.dto.schedule.RestaurantDto;
import com.dongyang.seoulTravel.dto.schedule.SpotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class PlaceApiService {

    private final RestaurantApiService restaurantApiService;
    private final AccommodationApiService accommodationApiService;
    private final SpotApiService spotApiService;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

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


    // 최단 거리 알고리즘 설정.............
    public List<PlaceDto> optimizeTravelPlan(List<PlaceDto> places) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://apis-navi.kakaomobility.com/v1/waypoints/directions";
        StringBuilder coords = new StringBuilder();
        for (PlaceDto place : places) {
            coords.append(place.getLongitude()).append(",").append(place.getLatitude()).append("|");
        }
        if (coords.length() > 0) {
            coords.setLength(coords.length() - 1);
        }

        String requestUrl = apiUrl + "?waypoints=" + coords.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

        return parseOptimalRoute(response.getBody(), places);
    }

    private List<PlaceDto> parseOptimalRoute(String responseBody, List<PlaceDto> places) {

        return places; // 임시 반환 잘 모르겓음 ㅠ
    }
}
