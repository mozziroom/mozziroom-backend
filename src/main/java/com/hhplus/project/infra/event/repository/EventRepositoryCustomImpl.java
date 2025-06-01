package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventList;
import com.hhplus.project.infra.common.utils.BooleanBuilderUtil;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.querydsl.core.BooleanBuilder;
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
                .where(eventListFilter(command))
                .orderBy(order)
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long total = queryFactory.select(eventEntity.eventId.count())
                .from(eventEntity)
                .where(eventListFilter(command))
                .fetchOne();

        return new PageImpl<>(content.stream()
                .map(EventEntity::toDomain)
                .toList(),
                command.pageable(),
                total == null ? 0L : total);
    }

    private BooleanBuilder eventListFilter(EventList.Command command) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(BooleanBuilderUtil.nullSafeBuilder(() -> eventEntity.locationId.eq(command.locationId())));
        builder.and(BooleanBuilderUtil.nullSafeBuilder(() -> categoryEntity.categoryId.eq(command.categoryId())));
        builder.and(BooleanBuilderUtil.nullSafeBuilder(() -> eventEntity.startAt.goe(command.startAt())));
        builder.and(BooleanBuilderUtil.nullSafeBuilder(() -> eventEntity.endAt.loe(command.endAt())));
        builder.and(BooleanBuilderUtil.nullSafeBuilder(() -> eventEntity.name.containsIgnoreCase(command.keyword())));
        if (command.keyword() != null && !command.keyword().isEmpty()) {
            builder.and(eventEntity.name.containsIgnoreCase(command.keyword()));
        }
        return builder;
    }
}
