package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.EventImage;
import com.hhplus.project.domain.event.EventImageRepository;
import com.hhplus.project.infra.event.entity.EventImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventImageRepositoryImpl implements EventImageRepository {

    private final EventImageJpaRepository jpaRepository;
    private final EventImageFileRepository fileRepository;

    @Override
    public void saveAll(List<EventImage> eventImageList) {
        jpaRepository.saveAll(eventImageList.stream().map(EventImageEntity::fromDomain).toList());
    }

    @Override
    public String saveFile(MultipartFile multipartFile) {
        fileRepository.saveFile(multipartFile);
        return "filePath";
    }


}
