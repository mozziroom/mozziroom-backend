package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.EventTimeSlot;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "event_time_slot")
public class EventTimeSlotEntity {
    /**
     * 장소 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_time_slot_id")
    private Long eventTimeSlotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private EventEntity event;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "time_slot_id", nullable = false)
    private Long timeSlotId;

    public EventTimeSlot toDomain() {
        return new EventTimeSlot(
                eventTimeSlotId,
                event.toDomain(),
                eventDate,
                timeSlotId
        );
    }

    public static EventTimeSlotEntity fromDomain(EventTimeSlot eventTimeSlot) {
        EventTimeSlotEntity entity = new EventTimeSlotEntity();
        entity.eventTimeSlotId = eventTimeSlot.eventTimeSlotId();
        entity.event = EventEntity.fromDomain(eventTimeSlot.event(), null);
        entity.eventDate = eventTimeSlot.eventDate();
        entity.timeSlotId = eventTimeSlot.timeSlotId();
        return entity;
    }
}
