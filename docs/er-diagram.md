# ERD

``` mermaid
erDiagram
event ||--o{ reservation : "1:N"
member ||--o{ reservation : "1:N"
reservation ||--o{ reservation_history : "1:N"
recurring_rules ||--o{ event : "1:N"
event ||--o{ event_history : "1:N"
member ||--o{ event : "1:N"
event ||--o{ category : "1:1"
event ||--o{ location : "1:1"
event ||--o{ event_image : "1:1"
event_time_slot }o--|| event : "N:1"
event_time_slot }o--|| time_slot : "N:1"
 	
time_slot {
        bigint id PK "타임슬롯 id"
        time   start_time "시작시간"
        time   end_time "종료시간"
}

event_time_slot {
        bigint id PK "이벤트 타임슬롯 id"
        bigint event_id FK "이벤트 id"
        date   event_date "이벤트 일자"
        bigint slot_id FK "타임슬롯 id"
}

%% 멤버
member {
	bigint member_id PK "멤버 id"
	varchar name "이름"
	varchar nickname "닉네임"
	varchar profile_img_path "프로필 사진 PATH"
	varchar email "이메일"
	datetime created_at "생성일"
	datetime updated_at "수정일"
}

%% 스터디 모임
event {
	bigint event_id PK "스터디 id"
	bigint category_id "카테고리 id"
	varchar name "스터디명"
	longtext content "스터디 내용"
	datetime start_at "스터디 시작시간"
	datetime end_at "스터디 종료시간"
	bigint host_id "스터디 주최자 (member_id)"
	int capacity "정원"
	varchar is_online "온라인 여부"
	bigint location_id "지역 id"
	varchar location_detail "상세 장소"
	varchar approve_type "이벤트 승인 타입"
	bigint recurring_rules_id "스터디 반복 규칙 id"
	datetime created_at "생성일시"
	datetime updated_at "수정일시"
	datetime deleted_at "삭제일시"
}

%% 이벤트 이미지
event_image {
    bigint event_image_id PK "이미지 id"
    bigint event_id "이벤트 id"
    varchar image_type "이미지 타입 (대표이미지 등등..)"
    varchar origin_image_path "원본 파일 PATH"
    varchar image_path "파일 PATH"
    int sort "정렬 순서"
    datetime created_at "생성일"
    datetime updated_at "수정일"
}

%% 이벤트 히스토리
event_history {
	bigint event_history_id PK "이벤트 히스토리 id"
	bigint event_id "스터디 id"
	bigint category_id "카테고리 id"
	varchar name "스터디명"
	longtext content "스터디 내용"
	datetime start_at "스터디 시작시간"
	datetime end_at "스터디 종료시간"
	bigint host_id "스터디 주최자 (member_id)"
	int capacity "정원"
	varchar is_online "온라인 여부"
	bigint location_id "지역 id"
	varchar location_detail "상세 장소"
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
	int recurring_interval "주기"
	date start_date "반복 시작일"
	date end_date "반복 종료일"
	datetime created_at "생성일시"
	datetime updated_at "수정일시"
	datetime deleted_at "삭제일시"
}

%% 카테고리	
category {
    bigint category_id PK "카테고리 id"
    varchar name "카테고리명"
    varchar is_active "사용 여부"
    bigint parent_id "상위 Depth id"
    int sort "정렬 순서"
    datetime created_at "생성일"
    datetime updated_at "수정일"
}

%% 지역
location {
    bigint location_id PK "장소 id"
    varchar region_code UK "행정구역 코드"
    varchar city "시"
    varchar district "구"
    varchar neighborhood "동"
    datetime created_at "생성일"
    datetime updated_at "수정일"
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
