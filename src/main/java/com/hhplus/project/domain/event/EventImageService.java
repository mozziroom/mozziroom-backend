package com.hhplus.project.domain.event;

import org.springframework.web.multipart.MultipartFile;

public interface EventImageService {
    void checkImage(MultipartFile file);
    Long saveImage(MultipartFile file);
}
