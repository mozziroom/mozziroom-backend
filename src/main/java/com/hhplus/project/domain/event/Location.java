package com.hhplus.project.domain.event;

public record Location(
        Long locationId,
        String regionCode,
        String city,
        String district,
        String neighborhood
) {

    public static Location create(String regionCode, String city, String district, String neighborhood) {
        return new Location(null, regionCode, city, district, neighborhood);
    }
}
