package com.hhplus.project.interfaces.member;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @LocalServerPort
    int port;

    @Test
    @DisplayName("사용자 회원 가입 API 호출 시 정상적으로  응답이 오는지 확인")
    void signUp() {
        // given
        SignUp.Request request =
                new SignUp.Request("김갑수", "kabsukim@naver.com", "kapsoo0428");

        SignUp.Response expectedResponse = SignUp.Response.signUpSuccess();

        // when
        ExtractableResponse<Response> response = RestAssured.given()
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/members")
                .then()
                .log().all()
                .extract();

        SignUp.Response signUpResponse = response.jsonPath().getObject("data", SignUp.Response.class);
        assertEquals(expectedResponse,signUpResponse);
    }

    @Test
    @DisplayName("사용자 정보 수정 API 호출 시 정상적으로 응답이 오는지 확인")
    void update() {
        // given
        Long memberId = 1L;
        Update.Request request = new Update.Request(Optional.of("kapsoo0428"), Optional.of("/users/member/101/adfafadsfdasfadsf.jpg"));

        // when
        ExtractableResponse<Response> response = RestAssured.given()
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/members/" + memberId)
                .then()
                .log().all()
                .extract();

        // then
        assertEquals(200, response.statusCode());
        String status = response.jsonPath().getString("status");
        assertEquals("200", status);
    }
}

