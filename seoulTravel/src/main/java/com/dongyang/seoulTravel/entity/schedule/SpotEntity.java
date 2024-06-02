package com.dongyang.seoulTravel.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SpotEntity extends PlaceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spotPostSn")
    private String spotPostSn;

    @Column(name = "spotLangCodeId")
    private String spotLangCodeId;

    @Column(name = "spotPostSj")
    private String spotPostSj;

    @Column(name = "spotPostUrl")
    private String spotPostUrl;

    @Column(name = "spotAddress")
    private String spotAddress;

    @Column(name = "spotNewAddress")
    private String spotNewAddress;

    @Column(name = "spotCmnnTelno")
    private String spotCmnnTelno;

    @Column(name = "spotSubwayInfo")
    private String spotSubwayInfo;

    @Column(name = "spotTag")
    private String spotTag;

}