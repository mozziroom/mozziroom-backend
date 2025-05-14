package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.domain.utils.ImageUploadUtil;
import jakarta.transaction.Transactional; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ImageUploadUtil imageUploadUtil;

    @Transactional
    public void update(UpdateEvent.Criteria criteria, MultipartFile file) {
        Event event = eventRepository.findById(criteria.eventId()); // 영속성 컨테스트 안에 없음
        // TODO - MultipartFile -> File로 변환해야함
        String imagePath = imageUploadUtil.upload(file);
        event.updateChangeData(criteria, imagePath);
        eventRepository.save(event);
    }
}
