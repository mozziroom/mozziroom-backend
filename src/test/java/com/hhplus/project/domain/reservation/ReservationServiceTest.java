package com.hhplus.project.domain.reservation;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.reservation.dto.CreateReservationCommand;
import com.hhplus.project.domain.reservation.dto.FindReservationListInfo;
import com.hhplus.project.domain.reservation.dto.UpdateReservationCommand;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.fixture.MemberFixture;
import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest extends BaseIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    @Autowired
    private EventFixture eventFixture;

    @Autowired
    private MemberFixture memberFixture;

    @Test
    @DisplayName("🟢 이벤트 예약 시, 이벤트가 생성되고 History에 반영된다.")
    void reserve(){
        // given
        long eventId = 1L;
        long memberId = 42L;
        var createCmd = new CreateReservationCommand.Command(eventId, memberId);

        // when
        Reservation saved = reservationService.reserve(createCmd);

        // then
        assertNotNull(saved.getReservationId(), "예약 ID가 생성되어야 한다");
        Reservation found = reservationRepository.findById(saved.getReservationId());
        assertEquals(eventId, found.getEventId());
        assertEquals(memberId, found.getMemberId());
        assertEquals(ReservationEnums.Status.PENDING, found.getStatus());

        List<ReservationHistory> histories = reservationHistoryRepository
                .findAllByReservationId(saved.getReservationId());
        assertEquals(1, histories.size(), "예약 시 히스토리가 1건 생성되어야 한다");
    }

    @Test
    @DisplayName("🟢 이벤트 취소 시, 해당 이벤트의 상태를 변경하고 History에 반영된다.")
    void cancel(){
        // given
        long eventId = 2L;
        long memberId = 99L;
        var createCmd = new CreateReservationCommand.Command(eventId, memberId);
        Reservation saved = reservationService.reserve(createCmd);

        // when
        var cancelCmd = new UpdateReservationCommand.Command(saved.getReservationId(), memberId);
        Reservation canceled = reservationService.cancel(cancelCmd);

        // then
        assertEquals(ReservationEnums.Status.CANCELED, canceled.getStatus(), "취소 후 상태가 CANCELED여야 한다");

        List<ReservationHistory> histories = reservationHistoryRepository
                .findAllByReservationId(saved.getReservationId());
        assertEquals(2, histories.size(), "취소 시 히스토리가 2건(PENDING+CANCELED) 생성되어야 한다");
    }

    @Test
    @DisplayName("🔴 이벤트 예약 수정(취소/거절/승인) 시, 해당 이벤트를 찾지 못하면 ReservationException(BaseException)이 발생한다.")
    void baseException_NOT_MATCHED_RESERVATION(){
        // given
        long notExistId = 9999L;
        long memberId = 999L;
        var badCmd = new UpdateReservationCommand.Command(notExistId, memberId);

        // when / then
        BaseException ex = assertThrows(BaseException.class,
                () -> reservationService.cancel(badCmd), "존재하지 않는 예약 ID로 취소 호출 시 예외가 발생해야 한다");
        assertEquals(ReservationException.NOT_MATCHED_RESERVATION.getCode(),
                ex.getCode());
    }

    @Test
    @DisplayName("🟢 예약을 한번 수행 후 예약 목록을 조회하면 한 개가 조회된다.")
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

        // when
        Page<FindReservationListInfo.Info> reservationList = reservationService.findReservationList(
                member.memberId(),
                PageRequest.of(0, 10)
        );

        // then
        assertThat(reservationList.getTotalElements()).isEqualTo(1L);
    }
}