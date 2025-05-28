package com.hhplus.project.application.event;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.application.event.dto.CreateEventFacade;
import com.hhplus.project.domain.event.*;
import com.hhplus.project.domain.member.MemberRepository;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.infra.event.entity.CategoryEntity;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.infra.event.entity.LocationEntity;
import com.hhplus.project.infra.event.repository.CategoryJpaRepository;
import com.hhplus.project.infra.event.repository.EventJpaRepository;
import com.hhplus.project.infra.event.repository.LocationJpaRepository;
import com.hhplus.project.infra.member.entity.MemberEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.hhplus.project.fixture.EventFixture.creatEventWithHost;
import static com.hhplus.project.fixture.MemberFixture.createMember;
import static com.hhplus.project.infra.event.entity.QEventEntity.eventEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventFacadeTest extends BaseIntegrationTest {

    @Autowired
    EventFacade eventFacade;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    LocationJpaRepository locationJpaRepository;

    @Autowired
    EventJpaRepository eventJpaRepository;

    private MemberEntity memberEntity;

    @BeforeEach
    void setUp() {
        memberEntity = memberRepository.save(createMember());
    }

    @Test
    @DisplayName("호스트가 이벤트를 조회하면 isHost는 true이다.")
    void getEventByHost() {
        // given
        Event event = eventRepository.save(creatEventWithHost(memberEntity.getMemberId()).toDomain());

        // when
        Long eventId = event.eventId();
        long memberId = memberEntity.getMemberId();
        GetEvent.Result eventResult = eventFacade.getEvent(eventId, memberId);

        // then
        assertThat(eventResult.isHost()).isTrue();
    }


    @Test
    @DisplayName("이벤트 호스트가 아닌 회원이 이벤트를 조회하면 예약상태가 리턴된다.")
    void getEvent() {
    }


    @Test
    @DisplayName("""
        EventFacade를 통해 Event를 생성한 후,
        해당 ID로 조회 시 생성한 정보와 일치해야 한다.
        """)
    void shouldCreateEventAndRetrieveSameEventById() {
        // given: 테스트용 카테고리와 장소 생성
        Location location = locationJpaRepository.save(LocationEntity.fromDomain(EventFixture.createLocation())).toDomain();
        Category category = categoryJpaRepository.save(CategoryEntity.fromDomain(EventFixture.createCategory())).toDomain();


        // 그리고: 이벤트 생성 조건 정의
        String expectedTitle = "test";
        String expectedContent = "통합 테스트 입니다";
        LocalDateTime startAt = LocalDateTime.of(2025, 5, 28, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 5, 28, 3, 0);
        int capacity = 25;
        boolean isOnline = false;
        String locationDetail = "test 입니다.";
        EventEnums.ApproveType approveType = EventEnums.ApproveType.MANUAL;

        CreateEventFacade.Criteria criteria = new CreateEventFacade.Criteria(
                category.categoryId(),
                location.locationId(),
                expectedTitle,
                expectedContent,
                startAt,
                endAt,
                "tempToken",
                capacity,
                approveType,
                isOnline,
                locationDetail
        );

        // when: 이벤트를 생성하고 해당 ID로 조회
        CreateEventFacade.Result result = eventFacade.createEvent(criteria);
        Optional<EventEntity> optionalEvent = eventJpaRepository.findById(result.eventId());

        // then: 조회된 이벤트가 존재하고, 생성한 값과 일치해야 한다
        assertTrue(optionalEvent.isPresent(), "이벤트가 저장되어 있어야 합니다.");
        EventEntity event = optionalEvent.get();

        assertAll("이벤트 필드 확인",
                () -> assertEquals(expectedTitle, event.getName(), "이벤트 제목이 일치하지 않습니다."),
                () -> assertEquals(expectedContent, event.getContent(), "이벤트 내용이 일치하지 않습니다."),
                () -> assertEquals(startAt, event.getStartAt(), "시작 시간이 일치하지 않습니다."),
                () -> assertEquals(endAt, event.getEndAt(), "종료 시간이 일치하지 않습니다."),
                () -> assertEquals(capacity, event.getCapacity(), "수용 인원이 일치하지 않습니다."),
                () -> assertEquals(isOnline, event.isOnline(), "온라인 여부가 일치하지 않습니다."),
                () -> assertEquals(locationDetail, event.getLocationDetail(), "장소 상세 정보가 일치하지 않습니다.")
        );
    }

}