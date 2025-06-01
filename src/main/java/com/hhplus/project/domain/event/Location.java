package com.hhplus.project.domain.event;

public record Location(
        Long locationId,
        String regionCode,
        String city,
        String district,
        String neighborhood
) {
}
