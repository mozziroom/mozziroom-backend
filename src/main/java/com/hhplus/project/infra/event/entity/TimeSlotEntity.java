package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.TimeSlot;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "time_slot")
public class TimeSlotEntity {
    /**
     * 장소 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private Long timeSlotId;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    public TimeSlot toDomain() {
        return new TimeSlot(
                timeSlotId,
                startTime,
                endTime
        );
    }

    public static TimeSlotEntity fromDomain(TimeSlot timeSlot) {
        TimeSlotEntity entity = new TimeSlotEntity();
        entity.timeSlotId = timeSlot.timeSlotId();
        entity.startTime = timeSlot.startTime();
        entity.endTime = timeSlot.endTime();
        return entity;
    }
}
