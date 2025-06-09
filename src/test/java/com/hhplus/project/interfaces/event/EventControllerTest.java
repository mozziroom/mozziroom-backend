package com.hhplus.project.interfaces.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.member.Member;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventControllerTest extends BaseIntegrationTest {

    @Autowired
    EventFixture eventFixture;

    @Autowired
    MemberFixture memberFixture;

    private Event event;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberFixture.create();
    }

    @Test
    @DisplayName("이벤트 상세 조회 API 호출 시 정상적으로 응답이 오는지 확인한다.")
    void getEventDetail() {
        // given
        event = eventFixture.create(member.memberId());
        long eventId = event.eventId();
        EventDetail.Response expectedResponse = EventDetail.Response.create();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .when()
                .get("/events/" + eventId)
                .then()
                .log().all().extract();

        String status = response.jsonPath().getString("status");
        EventDetail.Response eventDetailResponse = response.jsonPath().getObject("data", EventDetail.Response.class);

        // then
        assertNotNull(response);
        assertEquals("200", status);
        assertEquals(expectedResponse, eventDetailResponse);
    }

    @Test
    @DisplayName("이벤트 목록 조회 API 호출 시 정상적으로 응답이 반환되는지 확인한다.")
    void getEventList() {
        // given
        HashMap<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("page", 0);
        parametersMap.put("size", 10);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .queryParams(parametersMap)
                .when()
                .get("/events/list")
                .then()
                .log().all().extract();

        String status = response.jsonPath().getString("status");
        List<FindEventList.Response> content = response.jsonPath().getList("data.content", FindEventList.Response.class);

        // then
        assertEquals("200", status);
        assertNotNull(response);
        assertThat(content).hasSize(1)
                .first().satisfies(r -> {
                    assertEquals(1L, r.eventId());
                    assertEquals("백엔드 스터디 모집", r.name());
                });
        assertEquals(0, response.jsonPath().getInt("data.number"));
        assertEquals(10, response.jsonPath().getInt("data.size"));
        assertEquals(1, response.jsonPath().getInt("data.totalElements"));
        assertEquals(1, response.jsonPath().getInt("data.totalPages"));
    }

    @Test
    @DisplayName("이벤트 정보 수정 API 호출 시, 정상 응답(HTTP 200)이 반환되는지 확인한다.")
    void updateEvent() throws JsonProcessingException {
        // given
        LocalDateTime startAt = LocalDateTime.of(2999, 12, 31, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2999, 12, 31, 3, 0);
        event = eventFixture.createWithDate(member.memberId(), startAt, endAt);
        UpdateEvent.Request request = new UpdateEvent.Request(
                event.categoryId(),
                "서각코 모집",
                "스타벅스에서 모각코 하실 분!",
                LocalDateTime.of(2999, 12, 31, 10, 0),
                LocalDateTime.of(2999, 12, 31, 11, 0),
                30,
                EventEnums.ApproveType.AUTO,
                false,
                event.locationId(),
                "스타벅스 XX지점",
                null
        );

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = objectMapper.writeValueAsString(request);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .multiPart("request", "request.json", json, "application/json")
                .contentType(ContentType.MULTIPART)
                .when()
                .patch("/events/" + event.eventId())
                .then()
                .log().all()
                .extract();

        // then
        String status = response.jsonPath().getString("status");
        assertEquals("200", status);
    }

//    @Test
//    @DisplayName("🟢 이벤트 생성 API 호출 시, HTTP 200 상태코드가 리턴된다.")
//    void createEvent() throws JsonProcessingException {
//
//    }
}
