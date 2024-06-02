package com.dongyang.seoulTravel.entity.schedule;

import jakarta.persistence.*;

@MappedSuperclass
public class PlaceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

}
