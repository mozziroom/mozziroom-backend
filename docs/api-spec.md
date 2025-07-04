# API-Spec

- [이벤트 목록 조회](#%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EB%AA%A9%EB%A1%9D-%EC%A1%B0%ED%9A%8C)
- [이벤트 상세조회](#%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%83%81%EC%84%B8%EC%A1%B0%ED%9A%8C)
- [이벤트 등록](#%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EB%93%B1%EB%A1%9D)
- [이벤트 수정](#%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%88%98%EC%A0%95)
- [이벤트 예약/예약취소](#%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%98%88%EC%95%BD%EC%98%88%EC%95%BD%EC%B7%A8%EC%86%8C)

## 이벤트 목록 조회

- **Endpoint**: `GET /events/list`
- **Description**:
    - 이벤트 목록을 조회한다.

#### Query Parameters

| 파라미터        | 타입       | 설명                |  
|-------------|----------|-------------------|  
| keyword     | string   | 검색어               |  
| startAt     | datetime | 시작일시              |
| endAt       | datetime | 종료일시              |
| locationId  | number   | 지역 Id             |
| categoryId  | number   | 카테고리 Id           |
| sort        | string   | 정렬 기준(인기순, 정원순 등) |  
| page        | number   | 페이지 번호            |
| size        | number   | 페이지 사이즈           |

#### Response Body

```json  
[
  {
    "eventId": 101,
    "thumbnailImagePath": "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123954.jpg",
    "name": "백엔드 스터디 모집",
    "location": {
      "city": "서울시",
      "district": "성동구",
      "neighborhood": "성수동",
      "locationDetail": "성수 29cm"
    },
    "category": {
      "fullName": "스터디 > 개발 > 백엔드"
    },
    "startAt": "2025-04-11T18:40:00",
    "endAt": "2025-04-22T18:40:00"
  },
  {
    "eventId": 102,
    "thumbnailImagePath": "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123954.jpg",
    "name": "영어회화 스터디 모집",
    "currentCapacity": 3,
    "capacity": 8,
    "location": {
      "city": "서울시",
      "district": "강남구",
      "neighborhood": "역삼동",
      "locationDetail": "팀스파르타"
    },
    "category": {
      "fullName": "스터디 > 언어 > 영어"
    },
    "startAt": "2025-04-30T18:40:00",
    "endAt": "2025-04-31T18:40:00"
  }
]  
```  

| 필드명                | 타입       | 설명             |  
|--------------------|----------|----------------|  
| eventId            | number   | 이벤트 식별자        |
| thumbnailImagePath | string   | 이벤트 썸네일 이미지 경로 |
| name               | string   | 이벤트명           |
| location           | object   | 장소             |
| ⎿ city             | string   | 시              |
| ⎿ district         | string   | 구              |
| ⎿ neighborhood     | string   | 동              |
| ⎿ locationDetail   | string   | 상세 주소          |
| category           | object   | 카테고리           |
| ⎿ fullName         | string   | 카테고리 명         |
| startAt            | datetime | 시작일시           |
| endAt              | datetime | 종료일시           |

## 이벤트 상세조회

- **Endpoint**: `GET /events/{eventId}`
- **Description**: 특정 이벤트를 조회한다.

#### Path Parameter

| 파라미터명   | 타입     | 설명          |  
|---------|--------|-------------|  
| eventId | number | 이벤트 식별자(필수) |  

#### Response Body

```json  
{
  "eventDate": "2025-04-05",
  "startAt": "16:00",
  "endAt": "22:00",
  "place": "건대입구역",
  "content": "너와 함께라서 따뜻한 봄날이었다,,",
  "participants": [
    {
      "profile": "https://sample.com/12345",
      "nickname": "웅경"
    },
    {
      "profile": "https://sample.com/12346",
      "nickname": "지누"
    },
    {
      "profile": "https://sample.com/12346",
      "nickname": "시욱"
    },
    {
      "profile": "https://sample.com/12346",
      "nickname": "지츄"
    },
    {
      "profile": "https://sample.com/12346",
      "nickname": "쑤현"
    },
    {
      "profile": "https://sample.com/12346",
      "nickname": "히진"
    }
  ],
  "recurringRules": {
    "recurringRulesId": 5,
    "recurringType": "주간",
    "recurring_interval": 1,
    "startDate": "2025-04-10",
    "endDate": "2025-06-10"
  },
  "category": {
    "name": "투어"
  },
  "isOnline": "N",
  "location": {
    "city": "서울시",
    "district": "성동구",
    "neighborhood": "성수동",
    "locationDetail": "성수 29cm"
  },
  "eventImages": [
    {
      "eventImageId": 1,
      "eventImagePath": "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123954.jpg",
      "sort": 1,
      "imageType": "MAIN"
    },
    {
      "eventImageId": 2,
      "eventImagePath": "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123955.jpg",
      "sort": 2,
      "imageType": "OTHER"
    }
  ],
  "isHost": "N",
  "reservationStatus": "PENDING"
}
```  

| 필드명                  | 타입       | 설명          |  
|----------------------|----------|-------------|
| startAt              | datetime | 이벤트 시작 일시   |
| endAt                | datetime | 이벤트 종료 일시   |
| place                | string   | 장소          |
| content              | string   | 내용          |
| participants         | object   | 참여자         |
| ⎿ profile            | string   | 프로필 이미지 URL |
| ⎿ nickname           | string   | 참석자 닉네임     |
| recurringRules       | object   | 반복 일정       |
| ⎿ recurringRulesId   | number   | 반복 규칙 식별자   |
| ⎿ recurringType      | string   | 반복 타입       |
| ⎿ recurring_interval | number   | 반복 횟수       |
| ⎿ startDate          | date     | 시작일         |
| ⎿ endDate            | date     | 종료일         |
| category             | object   | 카테고리        |
| ⎿name                | string   | 카테고리명       |
| isOnline             | string   | 반복 일정       |
| location             | object   | 장소          |
| ⎿city                | string   | 시           |
| ⎿district            | string   | 구           |
| ⎿neighborhood        | string   | 동           |
| ⎿locationDetail      | string   | 상세 주소       |
| eventImages          | object   | 반복 일정       |
| ⎿ eventImageId       | number   | 이미지 ID      |
| ⎿ eventImagePath     | string   | 이미지 경로      |
| ⎿ sort               | number   | 정렬 순서       |
| isHost               | string   | 호스트 여부      |
| reservationStatus    | string   | 예약 상태       |

## 이벤트 등록

- **Endpoint**: `POST /events`
- **Description**:
    - 이벤트를 등록한다.
    - JSON 형식의 이벤트 정보와 이미지 파일(image)을 함께 multipart/form-data로 전송

#### Header

| 필드명         | 타입     | 설명    |  
|-------------|--------|-------|  
| AccessToken | string | 인증 토큰 |

#### Request Body

```json
{
  "categoryId": 1,
  "name": "서각코 모집",
  "content": "서각코 모집을 위한 이벤트입니다.",
  "startAt": "2025-04-10T14:00:00",
  "endAt": "2025-04-10T16:00:00",
  "host_id": 100,
  "capacity": 30,
  "approveType": "AUTO",
  "isOnline": false,
  "locationId": 2,
  "locationDetail": "서울 강남구 스타벅스",
  "recurringRules": {
    "recurringType": "WEEK",
    "recurring_interval": 1,
    "startAt": "2025-04-10",
    "endAt": "2025-06-10"
  }
}
```

#### Response Body

성공 적으로 event 가 생성이 되었을 때

```json
{
  "status": "201",
  "message": "Event Create Successfully",
  "detail": {
    "eventId": "1"
  }
}
```

생성 할 수 있는 event 수가 초과 했을 경우

```json
{
  "status": "404",
  "message": "The maximum number of creatable rooms has been exceeded.",
  "detail": {}
}
```

입력 값을 잘못 입력 하였을때 - Event Title이 정규식에 맞지 않을 때

```json
{
  "status": "400",
  "message": "Invalid Event Title",
  "detail": {
    "reason": "The title contains invalid characters or does not match the required format."
  }
}
```

입력 값을 잘못 입력 하였을때 - 시작 날짜가 종료 날짜보다 나중일 때

```json
{
  "status": "400",
  "message": "Date Error",
  "detail": {
    "reason": "The start date cannot be later than the end date."
  }
}
```
| 필드명             | 타입       | 설명                                    |
|--------------------|------------|-----------------------------------------|
| categoryId         | number     | 카테고리 ID                            |
| name               | string     | 이벤트 이름                            |
| content            | string     | 스터디 내용                            |
| startAt            | datetime   | 시작 일시 (2025-04-10T14:00:00)        |
| endAt              | datetime   | 종료 일시 (2025-04-10T16:00:00)        |
| host_id            | number     | 주최자 식별자                          |
| capacity           | number     | 정원                                  |
| approveType        | string     | 승인 타입 (AUTO, MANUAL)              |
| isOnline           | boolean    | 온라인 여부 (true or false)           |
| locationId         | number     | 지역 ID                              |
| locationDetail     | string     | 상세 장소 (선택)                      |
| recurringRules     | object     | 반복 규칙 정보 (없을 경우 null)        |
| ⎿ recurringRulesId | number     | 반복 규칙 식별자 (기등록된 반복 ID 사용 시) |
| ⎿ recurringType    | string     | 반복 타입 (YEAR, MON, WEEK, DAY)      |
| ⎿ recurring_interval | number   | 반복 주기 (예: 매주 → 1)             |
| ⎿ startAt          | date       | 반복 시작일 (2025-04-10)             |
| ⎿ endAt            | date       | 반복 종료일 (2025-06-10)             |
| image              | file       | 썸네일 이미지 파일 (image/png, image/jpeg 등, 선택) |


## 이벤트 수정

- **Endpoint**: `PATCH /events/{eventId}`
- **Description**: 특정 이벤트를 수정한다.

#### Header

| 필드명         | 타입     | 설명    |  
|-------------|--------|-------|  
| AccessToken | string | 인증 토큰 |

#### Path Parameter

| 파라미터명   | 타입     | 설명          |  
|---------|--------|-------------|  
| eventId | number | 이벤트 식별자(필수) |  

#### Request Body

```json  
{
  "categoryId": 123,
  "name": "서각코 모집",
  "content": "스타벅스에서 모각코 하실 분!",
  "startAt": "2025-04-10T14:00:00",
  "endAt": "2025-04-10T16:00:00",
  "host_id": 1002,
  "capacity": 30,
  "approveType": "AUTO",
  "isOnline": false,
  "locationId": 12,
  "locationDetail": "스타벅스 XX지점",
  "eventImage": {
    "eventImageId": 234,
    "imagePath": "sample.jpg"
  },
  "recurringRules": {
    "recurringRulesId": 5,
    "recurringType": "WEEK",
    "recurring_interval": 1,
    "startAt": "2025-04-10",
    "endAt": "2025-06-10"
  }
}
```  

| 필드명                  | 타입       | 설명        |  
|----------------------|----------|-----------|
| categoryId           | number   | 카테고리 id   |
| name                 | string   | 이벤트 명     |
| content              | string   | 스터디 내용    |
| startAt              | datetime | 시작일시      |
| endAt                | datetime | 종료일시      |
| host_id              | number   | 주최자 식별자   |
| capacity             | number   | 정원        |
| approveType          | string   | 승인 타입     |
| isOnline             | boolean  | 온라인 여부    |
| locationId           | number   | 지역 id     |
| locationDetail       | string   | 상세 장소     |
| recurringRules       | object   | 반복 규칙     |
| ⎿ recurringRulesId   | number   | 반복 규칙 식별자 |
| ⎿ recurringType      | string   | 반복 타입     |
| ⎿ recurring_interval | number   | 반복 횟수     |
| ⎿ startAt            | date     | 시작일       |
| ⎿ endAt              | date     | 종료일       |

#### Response Body

```json
{
  "status": "200",
  "data": null,
  "error": null
}
```

## 이벤트 예약/예약취소


