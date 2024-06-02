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


    @Override
    public String getName() {
        return spotPostSj;
    }

}
