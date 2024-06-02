package com.dongyang.seoulTravel.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RestaurantEntity extends PlaceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restauPostSn")
    private String restauPostSn;

    @Column(name = "restauLangCodeId")
    private String restauLangCodeId;

    @Column(name = "restauPostSj")
    private String restauPostSj;

    @Column(name = "restauPostUrl")
    private String restauPostUrl;

    @Column(name = "restauAddress")
    private String restauAddress;

    @Column(name = "restauNewAddress")
    private String restauNewAddress;

    @Column(name = "restauCmnnTelno")
    private String restauCmnnTelno;

    @Column(name = "restauSubwayInfo")
    private String restauSubwayInfo;

    @Column(name = "restauTag")
    private String restauTag;

}