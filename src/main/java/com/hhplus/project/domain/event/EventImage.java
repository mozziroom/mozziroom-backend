package com.hhplus.project.domain.event;

public record EventImage(
        Long eventImageId,
        Event event,
        EventEnums.ImageType imageType,
        String originImagePath,
        String imagePath,
        int sort
) {
}
