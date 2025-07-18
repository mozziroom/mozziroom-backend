# FlowChart

- [이벤트 목록 조회](#이벤트-목록-조회)
- [이벤트 상세조회](#이벤트-상세-조회)
- [이벤트 등록](#이벤트-등록)
- [이벤트 수정](#이벤트-수정)
- [이벤트 예약/예약취소](#이벤트-예약예약취소)

## 이벤트 목록 조회
```mermaid
flowchart TD
    요청([요청]) --> 파라미터{파라미터 체크}

    subgraph FACETS["검색 조건/패싯 생성"]
        direction TB
        파라미터 -->|키워드| F1[키워드 패싯]
        파라미터 -->|기간| F2[기간 패싯]
        파라미터 -->|지역| F3[지역 패싯]
        파라미터 -->|카테고리| F4[카테고리 패싯]
    end

    subgraph ORDER/PAGING["정렬/페이징"]
        direction TB
        파라미터 -->|정렬| ORDER[ORDER BY]
        파라미터 -->|page/size| PAGING[OFFSET/LIMIT]
    end

    FACETS ---> 목록검색[목록 검색 실행]
    ORDER/PAGING --> 목록검색
    목록검색 --> 목록반환([이벤트 목록 반환])
```


## 이벤트 상세 조회
```mermaid
flowchart TD
    시작([시작]) --> 요청([이벤트 상세 조회 요청])
    요청 --> 스터디조회([스터디 ID로 스터디 정보 조회])
    스터디조회 --> 스터디존재확인{스터디가 존재하는가?}
    스터디존재확인 -- 아니오 --> 에러반환(["스터디를 찾을 수 없습니다" 에러 반환])
    스터디존재확인 -- 예 --> 호스트확인{요청한 사용자가 호스트인가?}
    호스트확인 -- 예 --> 호스트정보반환([호스트 권한 포함 스터디 상세 정보 반환])
    호스트확인 -- 아니오 --> 예약상태조회([사용자의 참석 신청/승인 상태 조회])
    예약상태조회 --> 사용자정보반환([예약 상태 포함 스터디 상세 정보 반환])
    호스트정보반환 --> 종료([종료])
    사용자정보반환 --> 종료
    에러반환 --> 종료

```

## 이벤트 등록

## 이벤트 수정
```mermaid
flowchart TD
    A[이벤트 수정 버튼 클릭] --> B{이벤트 기간이 종료되었는가?}
    B -- 예 --> C[종료된 스터디 오류 반환]
    C --> I[종료]
    B -- 아니오 --> D[이벤트 수정 페이지로 이동]
    D --> E[이벤트 수정 정보 입력]
    E --> F{입력값이 유효한가?}
    F -- 예 --> G[이벤트 데이터 업데이트 완료]
    F -- 아니오 --> H[얼럿 노출]
    H --> E
    G --> I[종료]
```

## 이벤트 예약/예약취소
### 이벤트 예약
```mermaid
flowchart TD
    시작([시작]) --> 요청[이벤트 예약 요청]
    요청 --> 이벤트조회[이벤트 ID로 이벤트 정보 조회]
    이벤트조회 --> 이벤트존재확인{이벤트가 존재하는가?}
    이벤트존재확인 -- 아니오 --> 이벤트에러반환["이벤트를 찾을 수 없습니다" 에러 반환]
    이벤트존재확인 -- 예 --> 이벤트정원확인{이벤트 정원이 초과되었는가?}
    이벤트정원확인 -- 예 --> 예약에러반환["이벤트 정원이 초과되었습니다" 에러 반환]
    이벤트정원확인 -- 아니오 --> 이벤트예약[예약정보 등록 및 history 기록]
    이벤트예약 --> 예약정보반환[예약 정보 반환]
    예약정보반환 --> 종료([종료])
    이벤트에러반환 --> 종료([종료])
    예약에러반환 --> 종료([종료])
```

### 이벤트 예약취소
```mermaid
flowchart TD
    시작([시작]) --> 요청[이벤트 예약취소 요청]
    요청 --> 이벤트조회[이벤트 ID로 이벤트 정보 조회]
    이벤트조회 --> 이벤트존재확인{이벤트가 존재하는가?}
    이벤트존재확인 -- 아니오 --> 이벤트에러반환["이벤트를 찾을 수 없습니다" 에러 반환]
    이벤트존재확인 -- 예 --> 이벤트예약확인{요청자와 예약자가 일치하는가?}
    이벤트예약확인 -- 아니오 --> 예약에러반환["다른 사용자의 예약정보입니다" 에러 반환]
    이벤트예약확인 -- 예 --> 이벤트예약취소[예약상태 업데이트 및 history 기록]
    이벤트예약취소 --> 예약취소정보반환[예약취소 정보 반환]
    예약취소정보반환 --> 종료([종료])
    이벤트에러반환 --> 종료([종료])
    예약에러반환 --> 종료([종료])
```
