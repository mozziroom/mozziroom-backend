package com.hhplus.project.infra.reservation;

import com.hhplus.project.domain.reservation.dto.FindReservationListInfo;
import com.hhplus.project.infra.reservation.dto.ReservationData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hhplus.project.infra.event.entity.QEventEntity.eventEntity;
import static com.hhplus.project.infra.member.entity.QMemberEntity.memberEntity;
import static com.hhplus.project.infra.reservation.entity.QReservationEntity.reservationEntity;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindReservationListInfo.Info> findReservationList(Long memberId, Pageable pageable) {
        List<ReservationData> content = queryFactory
                .select(Projections.constructor(ReservationData.class,
                        reservationEntity.reservationId,
                        reservationEntity.eventId,
                        eventEntity.name,
                        eventEntity.content,
                        eventEntity.startAt,
                        eventEntity.endAt,
                        reservationEntity.status,
                        memberEntity.name.as("hostName")
                ))
                .from(reservationEntity)
                .join(eventEntity).on(reservationEntity.eventId.eq(eventEntity.eventId))
                .join(memberEntity).on(eventEntity.hostId.eq(memberEntity.memberId))
                .where(reservationEntity.memberId.eq(memberId)
                        .and(eventEntity.deletedAt.isNull()))
                .orderBy(eventEntity.startAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        Long totalCount = queryFactory
                .select(reservationEntity.count())
                .from(reservationEntity)
                .join(eventEntity).on(reservationEntity.eventId.eq(eventEntity.eventId))
                .join(memberEntity).on(eventEntity.hostId.eq(memberEntity.memberId))
                .where(reservationEntity.memberId.eq(memberId)
                        .and(eventEntity.deletedAt.isNull()))
                .fetchOne();

        return new PageImpl<>(content.stream()
                .map(ReservationData::toInfo)
                .toList(),
                pageable,
                totalCount != null ? totalCount : 0L);
    }
}
