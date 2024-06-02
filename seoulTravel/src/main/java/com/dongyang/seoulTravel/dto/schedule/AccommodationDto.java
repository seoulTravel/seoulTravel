package com.dongyang.seoulTravel.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccommodationDto implements PlaceDto{

    @JsonProperty("MGTNO")
    private String accoMgtNo; //관리 번호

    @JsonProperty("SITETEL")
    private String accoSiteTel;

    @JsonProperty("SITEPOSTNO")
    private String accoSitePostNo;

    @JsonProperty("SITEWHLADDR") //지번번호
    private String accoSiteWhlAddr;

    @JsonProperty("RDNWHLADDR") //도로명 주소
    private String accoRdnWhlAddr;

    @JsonProperty("RDNPOSTNO") //도로명 우편번호
    private String accoRdnPostNo;

    @JsonProperty("BPLCNM") //숙소 이름
    private String accoBplcnm;

    @JsonProperty("UPTAENM") //업태구분명
    private String accoUptaenm;

    @JsonProperty("X") //좌표정보 - 경도
    private String longitude;

    @JsonProperty("Y") //좌표정보 - 위도
    private String latitude;

    @Override
    public String getName() {
        return accoBplcnm;
    }

}
