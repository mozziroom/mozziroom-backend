package com.hhplus.project.domain.notification;

import com.hhplus.project.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "notification")
@Getter
public class Notification extends BaseTimeEntity {
    /** 알림 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    /** 수신자 id */
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    /** 알림 제목 */
    @Column(name = "title", nullable = false)
    private String title;

    /** 알림 내용 */
    @Column(name = "content", nullable = false)
    private String content;

    /** 알림 구분 (예약 승인, 예약 취소 등) */
    @Column(name = "type", nullable = false)
    private NotificationEnums.Type type;
}
