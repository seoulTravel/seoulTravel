package com.dongyang.seoulTravel.service.scheduleService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


// 카카오 API 활용해서 위경도 추출
@Service
public class KakaoGeoService {

    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_API_KEY = "bbd6e2e8ba16d6cf3a11a4cfd3fda7b9";

    public String[] getLatLng(String address) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_API_URL + "?query=" + address,
                HttpMethod.GET,
                entity,
                String.class);

        String responseBody = response.getBody();

        // JSON 파싱하여 위도와 경도 추출
        return parseLatLng(responseBody);
    }

    private String[] parseLatLng(String responseBody) {
        String[] latLng = new String[2]; // 0: 위도, 1: 경도
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseBody);
            JsonNode documents = root.path("documents");
            JsonNode document = documents.get(0);

            String latitude = document.path("y").asText();
            String longitude = document.path("x").asText();

            latLng[0] = latitude;
            latLng[1] = longitude;


        } catch (Exception e) {
            e.printStackTrace();
        }


        return latLng;
    }

    // 잘 돌아가는 지 확인용 ^^,,,
    public static void main(String[] args) {
        KakaoGeoService service = new KakaoGeoService();
        String address = "서울특별시 강남구 테헤란로 123";
        String[] latLng = service.getLatLng(address);

        System.out.println("Latitude: " + latLng[0]);
        System.out.println("Longitude: " + latLng[1]);
    }


}
