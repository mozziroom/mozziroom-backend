package com.hhplus.project.domain.event;

import com.hhplus.project.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "location")
@Getter
public class Location extends BaseTimeEntity {
    /** 장소 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    /** 행정구역 코드 */
    @Column(name = "region_code", nullable = false)
    private String regionCode;

    /** 시 */
    @Column(name = "city", nullable = false)
    private String city;

    /** 구 */
    @Column(name = "district", nullable = false)
    private String district;

    /** 동 */
    @Column(name = "neighborhood")
    private String neighborhood;
}
