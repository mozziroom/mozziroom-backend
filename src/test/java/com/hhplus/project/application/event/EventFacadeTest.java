package com.hhplus.project.application.event;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventRepository;
import com.hhplus.project.domain.member.MemberRepository;
import com.hhplus.project.infra.member.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.hhplus.project.fixture.EventFixture.creatEventWithHost;
import static com.hhplus.project.fixture.MemberFixture.createMember;
import static com.hhplus.project.infra.event.entity.QEventEntity.eventEntity;
import static org.assertj.core.api.Assertions.assertThat;

class EventFacadeTest extends BaseIntegrationTest {

    @Autowired
    EventFacade eventFacade;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MemberRepository memberRepository;

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
}