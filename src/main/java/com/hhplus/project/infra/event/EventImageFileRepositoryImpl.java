package com.hhplus.project.infra.event;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


@Repository
@RequiredArgsConstructor
public class EventImageFileRepositoryImpl implements EventImageFileRepository {

    @Override
    public void saveFile(MultipartFile multipartFile) {

    }
}
