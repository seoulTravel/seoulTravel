package com.dongyang.seoulTravel.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpotDto {

    @JsonProperty("post_sn")
    private String postSn;

    @JsonProperty("lang_code_id")
    private String langCodeId;

    @JsonProperty("post_sj")
    private String postSj;

    @JsonProperty("post_url")
    private String postUrl;

    @JsonProperty("address")
    private String address;

    @JsonProperty("new_address")
    private String newAddress;

    @JsonProperty("cmnn_telno")
    private String cmnnTelno;

    @JsonProperty("subway_info")
    private String subwayInfo;

    @JsonProperty("tag")
    private String tag;

}
