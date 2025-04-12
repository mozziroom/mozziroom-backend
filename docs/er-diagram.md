# ERD

``` mermaid
erDiagram
event ||--o{ reservation : "1:N"
member ||--o{ reservation : "1:N"
reservation ||--o{ reservation_history : "1:N"
recurring_rules ||--o{ event : "1:N"
member ||--o{ event : "1:N"

%% 멤버
member {
	bigint member_id PK "멤버 id"
	varchar name "이름"
	varchar profile_img_url "프로필 사진 URL"
	varchar email "이메일"
	datetime created_at "생성일"
	datetime updated_at "수정일"
}

%% 스터디 모임
event {
	bigint event_id PK "스터디 id"
	varchar name "스터디명"
	longtext content "스터디 내용"
	datetime started_at "스터디 시작시간"
	datetime ended_at "스터디 종료시간"
	bigint host_id "스터디 주최자 (member_id)"
	int capacity "정원"
	varchar place "장소"
	varchar approve_type "이벤트 승인 타입"
	bigint recurring_rules_id "스터디 반복 규칙 id"
	datetime created_at "생성일시"
	datetime updated_at "수정일시"
	datetime deleted_at "삭제일시"
}

%% 이벤트 히스토리
event_history {
	bigint event_history_id PK "이벤트 히스토리 id"
	bigint event_id "스터디 id"
	varchar name "스터디명"
	longtext content "스터디 내용"
	datetime started_at "스터디 시작시간"
	datetime ended_at "스터디 종료시간"
	bigint host_id "스터디 주최자 (member_id)"
	int capacity "정원"
	varchar place "장소"
	varchar approve_type "이벤트 승인 타입"
	bigint recurring_rules_id "스터디 반복 규칙 id"
	datetime created_at "생성일시"
	datetime updated_at "수정일시"
	datetime deleted_at "삭제일시"
}

%% 스터디 반복 규칙
recurring_rules {
	bigint recurring_rules_id PK "스터디 반복 규칙 id"
	varchar recurring_type "반복 타입 (일간, 주간, 월간, 년간)"
	int interval "주기"
	date start_date "반복 시작일"
	date end_date "반복 종료일"
	datetime created_at "생성일시"
	datetime updated_at "수정일시"
	datetime deleted_at "삭제일시"
}

%% 예약
reservation {
	bigint reservation_id PK "예약 id"
	bigint event_id "스터디 id"
	bigint member_id "예약자 id"
	varchar status "예약 상태 (승인대기, 승인, 취소, 거절)"
	datetime created_at "생성일시"
	datetime updated_at "수정일시"
}

%% 예약 내역
reservation_history {
	bigint reservation_history_id PK "예약 내역 id"
	bigint reservation_id "예약 id"
	varchar status "상태"
	datetime created_at "생성일"
	datetime updated_at "수정일"
}

%% 알림
notification {
	bigint notification_id PK "알림 id"
	bigint member_id "수신자 id"
	varchar title "알림 제목"
	varchar content "알림 내용"
	varchar type "알림 구분 (예약 승인, 예약 취소 등)"
	datetime created_at "생성일"
	datetime updated_at "수정일"
}

```
