package com.dongyang.seoulTravel.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AccommodationEntity extends PlaceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accoMgtNo")
    private String accoMgtNo;

    @Column(name = "accoSiteTel")
    private String accoSiteTel;

    @Column(name = "accoSitePostNo")
    private String accoSitePostNo;

    @Column(name = "accoSiteWhlAddr")
    private String accoSiteWhlAddr;

    @Column(name = "accoRdnWhlAddr")
    private String accoRdnWhlAddr;

    @Column(name = "accoRdnPostNo")
    private String accoRdnPostNo;

    @Column(name = "accoBplcnm")
    private String accoBplcnm;

    @Column(name = "accoUptaenm")
    private String accoUptaenm;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

}