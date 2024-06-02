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


    @Override
    public String getName() {
        return restauPostSj;
    }


}
