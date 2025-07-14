package com.hhplus.project.infra.reservation;

import com.hhplus.project.domain.reservation.dto.FindReservationListInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryCustom {
    Page<FindReservationListInfo.Info> findReservationList(Long memberId, Pageable pageable);
}
