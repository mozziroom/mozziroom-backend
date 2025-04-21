package com.hhplus.project.domain.notification;

public class NotificationEnums {
    public enum Type {
        RESERVATION_APPROVED,     // 예약 승인
        RESERVATION_CANCELED,     // 예약 취소
        RESERVATION_REJECTED,     // 예약 거절
        RESERVATION_PENDING,      // 예약 승인대기 알림
        EVENT_UPDATED,            // 이벤트 내용 변경 알림
        EVENT_CANCELED,           // 이벤트 취소 알림
        GENERAL                   // 기타 일반 알림
    }
}
