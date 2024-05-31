package com.dongyang.seoulTravel.service.scheduleService;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.dongyang.seoulTravel.dto.schedule.SpotDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SpotApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<SpotDto> getAllSpots() throws Exception {
        List<SpotDto> allSpots = new ArrayList<>();
        int start = 1;
        int end = 1000;
        boolean hasMoreData = true;

        while (hasMoreData) {
            String url = String.format("http://openapi.seoul.go.kr:8088/4a75626b4e736838383947556e7664/json/TbVwAttractions/%d/%d/", start, end);
            String response = restTemplate.getForObject(url, String.class);

            try {
                JsonNode root = objectMapper.readTree(response);
                JsonNode rows = root.path("TbVwAttractions").path("row");
                if (rows.isArray()) {
                    int initialSize = allSpots.size();
                    for (JsonNode row : rows) {
                        SpotDto spotDto = objectMapper.treeToValue(row, SpotDto.class);
                        // 한국어만 따로 설정 ------------- 추후에 영어 넣을 수도 있을 것 같음 확인!
                        if ("ko".equals(spotDto.getSpotLangCodeId())) {
                            allSpots.add(spotDto);
                        }
                    }
                    hasMoreData = allSpots.size() > initialSize; // 만약 새로운 데이터가 추가되지 않았다면 더 이상 데이터가 없는 것으로 간주
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

        return allSpots;
    }

    // 장소 검색
    public List<SpotDto> searchSpots(String keyword) throws Exception {
        List<SpotDto> spots = getAllSpots();

        // 키워드 겁색 - 소문자
        String normalizedKeyword = keyword.trim().toLowerCase();

        // 필터링
        // 장소명 / 태그
        return spots.stream()
                .filter(spot -> {
                    boolean matches = false;
                    if (spot.getSpotPostSj() != null) {
                        matches = spot.getSpotPostSj().toLowerCase().contains(normalizedKeyword);
                    }
                    if (!matches && spot.getSpotTag() != null) {
                        matches = spot.getSpotTag().toLowerCase().contains(normalizedKeyword);
                    }
                    return matches;
                })
                .collect(Collectors.toList());
    }
}
