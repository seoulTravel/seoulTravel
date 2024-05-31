package com.dongyang.seoulTravel.service.scheduleService;
// 인증키 

import com.dongyang.seoulTravel.dto.schedule.RestaurantDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RestaurantApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // 식당 리스트 보기
    public List<RestaurantDto> getAllRestaurant() throws Exception {
        List<RestaurantDto> allRestaurants = new ArrayList<>();
        int start = 1;
        int end = 1000;
        boolean hasMoreDate = true;

        while (hasMoreDate) {
            String url = String.format("http://openapi.seoul.go.kr:8088/68417a52797368383734714f674f4d/json/TbVwRestaurants/%d/%d/", start, end);
            String response = restTemplate.getForObject(url, String.class);

            try {
                JsonNode root = objectMapper.readTree(response);
                JsonNode rows = root.path("TbVwRestaurants").path("row");
                if (rows.isArray()) {
                    int initialSize = allRestaurants.size();
                    for (JsonNode row : rows) {
                        RestaurantDto restaurantDto = objectMapper.treeToValue(row, RestaurantDto.class);
                        // spot 과 동일하게 한글만 임시 설정
                        if ("ko".equals(restaurantDto.getRestauLangCodeId())) {
                            allRestaurants.add(restaurantDto);
                        }
                    }

                    hasMoreDate = allRestaurants.size() > initialSize;
                    start += 1000;
                    end += 1000;
                } else {
                    hasMoreDate = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ;
                throw new Exception("Failed API response: " + e.getMessage());
            }
        }

        return allRestaurants;
    }

    // 음식점 검색
    public List<RestaurantDto> searchRestaurants(String keyword) throws Exception {
        List<RestaurantDto> restaurants = getAllRestaurant();

        // 키워드 겁색 - 소문자
        String normalizedKeyword = keyword.trim().toLowerCase();

        // 필터링 - 장소와 동일하게 음식점명 + 태그
        return restaurants.stream()
                .filter(restaurant -> {
                    boolean matches = false;
                    if (restaurant.getRestauPostSj() != null) {
                        matches = restaurant.getRestauPostSj().toLowerCase().contains(normalizedKeyword);
                    }
                    if (!matches && restaurant.getRestauTag() != null) {
                        matches = restaurant.getRestauTag().toLowerCase().contains(normalizedKeyword);
                    }
                    return matches;
                })
                .collect(Collectors.toList());
    }
}
