package com.hhplus.project.application;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.application.reservation.ReservationFacade;
import com.hhplus.project.application.reservation.dto.CreateReservationCriteria;
import com.hhplus.project.domain.event.EventException;
import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationFacadeTest extends BaseIntegrationTest {

    @Autowired
    ReservationFacade reservationFacade;

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
        assertEquals(EventException.NOT_MATCHED_RESERVATION.getCode(),
                ex.getCode());
    }
}