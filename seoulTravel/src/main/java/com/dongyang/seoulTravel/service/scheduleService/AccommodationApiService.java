package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.AccommodationDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AccommodationApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // 숙박 리스트 보기
    public List<AccommodationDto> getAllAccommodation() throws Exception {
        List<AccommodationDto> allAccommodations = new ArrayList<>();
        int start = 1;
        int end = 1000;
        boolean hasMoreData = true;

        while (hasMoreData) {
            String url = String.format("http://openapi.seoul.go.kr:8088/665867574873683837384558684173/json/LOCALDATA_031103/%d/%d/", start, end);
            String response = restTemplate.getForObject(url, String.class);

            try {
                JsonNode root = objectMapper.readTree(response);
                JsonNode rows = root.path("LOCALDATA_031103").path("row");
                if (rows.isArray()) {
                    int initialSize = allAccommodations.size();
                    for (JsonNode row : rows) {
                        AccommodationDto accommodationDto = objectMapper.treeToValue(row, AccommodationDto.class);
                        allAccommodations.add(accommodationDto);
                    }

                    hasMoreData = allAccommodations.size() > initialSize;
                    start += 1000;
                    end += 1000;
                } else {
                    hasMoreData = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Failed API response: " + e.getMessage());
            }
        }

        return allAccommodations;
    }

    // 숙박 검색
    public List<AccommodationDto> searchAccommodations(String keyword) throws Exception{
        List<AccommodationDto> accommodations = getAllAccommodation();

        // 키워드 검색 - 소문자
        String normalizedKeyword = keyword.trim().toLowerCase();

        // 필터링 - 업소명
        return accommodations.stream()
                .filter(accommodation -> {
                    boolean matches = false;
                    if(accommodation.getAccoBplcnm() != null){
                        matches = accommodation.getAccoBplcnm().toLowerCase().contains(normalizedKeyword);
                    }
                    return matches;
                })
                .collect(Collectors.toList());
    }
}
