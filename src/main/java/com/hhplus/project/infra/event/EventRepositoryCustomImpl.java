package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventList;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hhplus.project.domain.event.EventEnums.ImageType.THUMBNAIL;
import static com.hhplus.project.infra.event.entity.QCategoryEntity.categoryEntity;
import static com.hhplus.project.infra.event.entity.QEventEntity.eventEntity;
import static com.hhplus.project.infra.event.entity.QEventImageEntity.eventImageEntity;

@Repository
@RequiredArgsConstructor
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Event> findEventList(EventList.Command command) {
        OrderSpecifier<?> order = null;
        if (command.sortType() != null) {
            order = switch (command.sortType()) {
                case POPULAR -> null;
                case MOST_APPLIED -> null;
                case NEW -> eventEntity.createdAt.desc();
            };
        }

        List<EventEntity> content = queryFactory.select(eventEntity)
                .from(eventEntity)
                .leftJoin(categoryEntity).on(eventEntity.categoryId.eq(categoryEntity.categoryId))
                .leftJoin(eventImageEntity).on(eventEntity.eventId.eq(eventImageEntity.eventImageId)
                        .and(eventImageEntity.imageType.eq(THUMBNAIL)))
                .where(eventEntity.locationId.eq(command.locationId()))
                .where(categoryEntity.categoryId.eq(command.categoryId()))
                .where(eventEntity.startAt.goe(command.startAt()))
                .where(eventEntity.endAt.loe(command.endAt()))
                .where(eventEntity.name.containsIgnoreCase(command.keyword()))
                .orderBy(order)
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long total = queryFactory.select(eventEntity.eventId.count())
                .from(eventEntity)
                .where(eventEntity.locationId.eq(command.locationId()))
                .where(eventEntity.categoryId.eq(command.categoryId()))
                .where(eventEntity.startAt.goe(command.startAt()))
                .where(eventEntity.endAt.loe(command.endAt()))
                .where(eventEntity.name.containsIgnoreCase(command.keyword()))
                .fetchOne();

        return new PageImpl<>(content.stream()
                .map(EventEntity::toDomain)
                .toList(),
                command.pageable(),
                total == null ? 0L : total);
    }
}
