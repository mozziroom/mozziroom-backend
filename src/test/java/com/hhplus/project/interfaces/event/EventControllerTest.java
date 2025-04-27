package com.hhplus.project.interfaces.event;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    @LocalServerPort
    int port;

    @Test
    @DisplayName("이벤트 상세 조회 API 호출 시 정상적으로 응답이 오는지 확인한다.")
    void getEventDetail() {
        // given
        long eventId = 1L;
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
        assertEquals(status, "200");
        assertEquals(expectedResponse, eventDetailResponse);
    }

    @Test
    @DisplayName("이벤트 정보 수정 API 호출 시, 정상 응답(HTTP 200)이 반환되는지 확인한다.")
    void updateEvent() {
        // given
        long eventId = 1L;
        UpdateEvent.Request request = new UpdateEvent.Request(
                "서각코 모집",
                LocalDateTime.of(2025, 4, 10, 14, 0),
                LocalDateTime.of(2025, 4, 10, 16, 0),
                30,
                "서울 강남구 스타벅스",
                EventEnums.ApproveType.AUTO,
                null
        );

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/events/" + eventId)
                .then()
                .log().all()
                .extract();

        // then
        String status = response.jsonPath().getString("status");
        assertEquals("200", status);
    }

    @Test
    @DisplayName("🟢 이벤트 생성 API 호출 시, HTTP 200 상태코드가 리턴된다.")
    void createEvent(){
        // given
        CreateEvent.RecurringRules rules = new CreateEvent.RecurringRules(
                RecurringRulesEnums.Type.DAY,
                7,
                LocalDate.of(2025, 4, 22),
                LocalDate.of(2025, 4, 29)
        );

        CreateEvent.Request request = new CreateEvent.Request(
                "서각코 모집",
                LocalDateTime.of(2025, 4, 22, 14, 0),
                LocalDateTime.of(2025, 4, 29, 16, 0),
                30,
                "온라인",
                EventEnums.ApproveType.AUTO,
                rules
        );

        //when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .port(port)
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post("/events")
                .then()
                .log().all()
                .extract();

        // then
        assertNotNull(response);
        assertEquals(200, response.statusCode());
    }
}