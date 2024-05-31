package com.dongyang.seoulTravel.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpotDto {

    @JsonProperty("POST_SN")
    private String postSn;

    @JsonProperty("LANG_CODE_ID")
    private String langCodeId;

    @JsonProperty("POST_SJ")
    private String postSj;

    @JsonProperty("POST_URL")
    private String postUrl;

    @JsonProperty("ADDRESS")
    private String address;

    @JsonProperty("NEW_ADDRESS")
    private String newAddress;

    @JsonProperty("CMMN_TELNO")
    private String cmnnTelno;

    @JsonProperty("SUBWAY_INFO")
    private String subwayInfo;

    @JsonProperty("TAG")
    private String tag;

    @Override
    public String toString() {
        return "SpotDto{" +
                "postSn='" + postSn + '\'' +
                ", langCodeId='" + langCodeId + '\'' +
                ", postSj='" + postSj + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", address='" + address + '\'' +
                ", newAddress='" + newAddress + '\'' +
                ", cmnnTelno='" + cmnnTelno + '\'' +
                ", subwayInfo='" + subwayInfo + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
