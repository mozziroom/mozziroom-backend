package com.hhplus.project.application;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.application.reservation.ReservationFacade;
import com.hhplus.project.application.reservation.dto.CreateReservationCriteria;
import com.hhplus.project.application.reservation.dto.FindReservationListResult;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.reservation.ReservationService;
import com.hhplus.project.domain.reservation.dto.CreateReservationCommand;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.fixture.MemberFixture;
import com.hhplus.project.support.BaseException;
import com.hhplus.project.support.security.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationFacadeTest extends BaseIntegrationTest {

    @Autowired
    ReservationFacade reservationFacade;

    @Autowired
    ReservationService reservationService;

    @Autowired
    EventFixture eventFixture;

    @Autowired
    MemberFixture memberFixture;

    @Autowired
    TokenProvider tokenProvider;

    @Test
    @DisplayName("🔴 이벤트 예약 시, 이벤트가 존재하지 않으면 EventException(BaseException)이 발생한다.")
    void baseException_EVENT_NOT_FOUND(){
        // given
        long notExistId = 9999L;
        long memberId = 999L;
        var createCmd = new CreateReservationCriteria.Criteria(notExistId, memberId);

        // when / then
        BaseException ex = assertThrows(BaseException.class,
                () -> reservationFacade.reserveEvent(createCmd), "존재하지 않는 이벤트를 예약 시, 예외가 발생해야 한다");
//        assertEquals(EventException.NOT_MATCHED_RESERVATION.getCode(),
//                ex.getCode());
    }

    @Test
    @DisplayName("AccessToken 에서 추출한 회원번호로 예약 목록을 조회한다.")
    void findReservationList() {
        // given
        Member host = memberFixture.create();
        Member member = memberFixture.create();
        Event event = eventFixture.create(host.memberId());

        CreateReservationCommand.Command command = new CreateReservationCommand.Command(
                event.eventId(),
                member.memberId(),
                EventEnums.ApproveType.AUTO
        );
        reservationService.reserve(command);

        String accessToken = tokenProvider.issueAccessToken(member.memberId(), "ROLE_USER");
        String authorization = "Bearer " + accessToken;

        // when
        Page<FindReservationListResult.Result> reservationList = reservationFacade.findReservationList(
                authorization,
                PageRequest.of(0, 10)
        );

        // then
        assertThat(reservationList.getTotalElements()).isEqualTo(1L);
    }
}