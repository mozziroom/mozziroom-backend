package com.hhplus.project.interfaces.event;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.application.reservation.ReservationFacade;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.member.Member;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.fixture.MemberFixture;
import com.hhplus.project.interfaces.reservation.dto.CreateReservationRequest;
import com.hhplus.project.interfaces.reservation.dto.FindReservationListResponse;
import com.hhplus.project.support.security.jwt.TokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest extends BaseIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    EventFixture eventFixture;

    @Autowired
    MemberFixture memberFixture;

    @Autowired
    ReservationFacade reservationFacade;

    @Autowired
    TokenProvider tokenProvider;

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

    @Test
    @DisplayName("예약 목록 조회 API 호출 시 정상적으로 응답이 반환되는지 확인한다.")
    void findReservationList() {
        // given
        Member host = memberFixture.create();
        Member member = memberFixture.create();
        Event event = eventFixture.create(host.memberId());

        CreateReservationRequest.Request request = new CreateReservationRequest.Request(
                event.eventId(),
                member.memberId()
        );
        reservationFacade.reserveEvent(request.toCriteria());

        HashMap<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("page", 0);
        parametersMap.put("size", 10);

        String accessToken = tokenProvider.issueAccessToken(member.memberId(), "ROLE_USER");
        String authorization = "Bearer " + accessToken;

        // when
        ExtractableResponse<Response> response = RestAssured
                .given()
                .header("Authorization", authorization)
                .queryParams(parametersMap)
                .when()
                .get("/reservations/list")
                .then()
                .log().all().extract();

        String status = response.jsonPath().getString("status");
        List<FindReservationListResponse.Response> content = response.jsonPath().getList("data.content", FindReservationListResponse.Response.class);

        assertEquals("200", status);
        assertNotNull(response);
        assertThat(content).hasSize(1)
                .first().satisfies(r -> {
                    assertEquals(event.eventId(), r.eventId());
                    assertEquals(event.name(), r.name());
                });

        assertEquals(0, response.jsonPath().getInt("data.number"));
        assertEquals(10, response.jsonPath().getInt("data.size"));
        assertEquals(1, response.jsonPath().getInt("data.totalElements"));
        assertEquals(1, response.jsonPath().getInt("data.totalPages"));
    }
}