package com.dongyang.seoulTravel.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantDto implements PlaceDto{

    @JsonProperty("POST_SN")
    private String restauPostSn;

    @JsonProperty("LANG_CODE_ID")
    private String restauLangCodeId;

    @JsonProperty("POST_SJ")
    private String restauPostSj;

    @JsonProperty("POST_URL")
    private String restauPostUrl;

    @JsonProperty("ADDRESS")
    private String restauAddress;

    @JsonProperty("NEW_ADDRESS")
    private String restauNewAddress;

    @JsonProperty("CMMN_TELNO")
    private String restauCmnnTelno;

    @JsonProperty("SUBWAY_INFO")
    private String restauSubwayInfo;

    @JsonProperty("TAG")
    private String restauTag;

    // 최단 거리 알고리즘 구현용
    //--------------------------------------------
    @JsonProperty("X") // 좌표정보 - 경도
    private String longitude;

    @JsonProperty("Y") // 좌표정보 - 위도
    private String latitude;

    @Override
    public String getName() {
        return restauPostSj;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }


}
