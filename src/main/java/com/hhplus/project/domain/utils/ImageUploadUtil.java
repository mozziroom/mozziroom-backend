package com.hhplus.project.domain.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ImageUploadUtil {

    String upload(MultipartFile file);
}
