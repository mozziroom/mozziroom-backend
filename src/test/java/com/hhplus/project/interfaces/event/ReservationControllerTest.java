package com.hhplus.project.interfaces.event;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Test
    @DisplayName("🟢 이벤트 예약 API 호출 시, HTTP 200 상태코드가 리턴된다.")
    void doReservation_ok(){
        // given
        long eventId = 1L;

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .port(port)
                .when()
                .post("/reservations/" + eventId)
                .then()
                .log().all()
                .extract();

        // then
        assertNotNull(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("🟢 이벤트 예약취소 API 호출 시, HTTP 200 상태코드가 리턴된다.")
    void cancelReservation_ok(){
        // given
        long eventId = 1L;

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .port(port)
                .when()
                .patch("/reservations/" + eventId)
                .then()
                .log().all()
                .extract();

        // then
        assertNotNull(response);
        assertEquals(200, response.statusCode());
    }
}