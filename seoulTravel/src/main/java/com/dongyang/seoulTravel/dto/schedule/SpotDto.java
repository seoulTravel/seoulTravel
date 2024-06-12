package com.dongyang.seoulTravel.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpotDto implements PlaceDto{

    @JsonProperty("POST_SN")
    private String spotPostSn;

    @JsonProperty("LANG_CODE_ID")
    private String spotLangCodeId;

    @JsonProperty("POST_SJ")
    private String spotPostSj;

    @JsonProperty("POST_URL")
    private String spotPostUrl;

    @JsonProperty("ADDRESS")
    private String spotAddress;

    @JsonProperty("NEW_ADDRESS")
    private String spotNewAddress;

    @JsonProperty("CMMN_TELNO")
    private String spotCmnnTelno;

    @JsonProperty("SUBWAY_INFO")
    private String spotSubwayInfo;

    @JsonProperty("TAG")
    private String spotTag;


    // 최단 거리 알고리즘 구현용
    //--------------------------------------------
    @JsonProperty("X") // 좌표정보 - 경도
    private String longitude;

    @JsonProperty("Y") // 좌표정보 - 위도
    private String latitude;

    @Override
    public String getName() {
        return spotPostSj;
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
