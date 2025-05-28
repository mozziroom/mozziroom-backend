package com.hhplus.project.domain.event;


import com.hhplus.project.domain.event.dto.CreateEvent;
import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventCreateTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService eventService;

    @DisplayName("""
            이벤트 생성 시, 
            존재하지 않는 Location Id 를 입력하는 경우
            BaseException 이 발생한다.
            """)
    @Test
    void NOT_FOUND_LOCATION(){
        // given
        CreateEvent.Command createCommand = new CreateEvent.Command(
                1L,
                1L,
                "test",
                "테스트입니다.",
                LocalDateTime.of(2025,5,28,0,0),
                LocalDateTime.of(2025,5,28,3,0),
                1L,
                20,
                EventEnums.ApproveType.AUTO,
                true,
                "테스트 입니다."
        );

        Category mockCategory = mock(Category.class);

        when(repository.findCategory(1L)).thenReturn(Optional.of(mockCategory));
        when(repository.findLocation(1L)).thenReturn(Optional.empty());


        // when
        BaseException exception = assertThrows(BaseException.class, ()->{
            eventService.create(createCommand);
        });

        // then
        Assertions.assertEquals(EventException.LOCATION_NOT_FOUND.getMessage(), exception.getMessage());
    }


    @DisplayName("""
            이벤트 생성 시, 
            존재하지 않는 Category Id 를 입력하는 경우
            BaseException 이 발생한다.
            """)
    @Test
    void NOT_FOUND_CATEGORY(){
        // given
        CreateEvent.Command createCommand = new CreateEvent.Command(
                1L,
                1L,
                "test",
                "테스트입니다.",
                LocalDateTime.of(2025,5,28,0,0),
                LocalDateTime.of(2025,5,28,3,0),
                1L,
                20,
                EventEnums.ApproveType.AUTO,
                true,
                "테스트 입니다."
        );

        when(repository.findCategory(1L)).thenReturn(Optional.empty());


        // when
        BaseException exception = assertThrows(BaseException.class, ()->{
            eventService.create(createCommand);
        });

        // then
        Assertions.assertEquals(EventException.CATEGORY_NOT_FOUND.getMessage(), exception.getMessage());
    }

}
