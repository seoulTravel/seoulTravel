package com.dongyang.seoulTravel.controller.schedule;

import com.dongyang.seoulTravel.dto.schedule.RestaurantDto;
import com.dongyang.seoulTravel.service.scheduleService.RestaurantApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantApiController {

    private final RestaurantApiService restaurantApiService;

    @Autowired
    public RestaurantApiController(RestaurantApiService restaurantApiService){
        this.restaurantApiService = restaurantApiService;
    }

    // 음식점 리스트 보기
    @GetMapping
    public List<RestaurantDto> getRestaurantList(){
        try{
            return restaurantApiService.getAllRestaurant();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 음식점 검색
    @GetMapping("/search")
    public List<RestaurantDto> searchRestaurant(@RequestParam(name = "keyword") String keyword){
        try{

            // 디버그 확인 메시지
            System.out.println("음식점 검색 Con 디버그 확인");
            return restaurantApiService.searchRestaurants(keyword);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
