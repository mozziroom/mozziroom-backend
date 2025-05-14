package com.hhplus.project.infra.event;

import org.springframework.web.multipart.MultipartFile;

public interface EventImageFileRepository {
    void saveFile(MultipartFile multipartFile);
}
