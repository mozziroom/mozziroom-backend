package com.hhplus.project.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventImageRepository {

    void saveAll(List<EventImage> eventImageList);

    String saveFile(MultipartFile multipartFile);
}
