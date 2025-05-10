package com.hhplus.project.infra.event.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "location")
public class LocationEntity extends BaseTimeEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity location = (LocationEntity) o;
        return Objects.equals(locationId, location.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId);
    }
}
