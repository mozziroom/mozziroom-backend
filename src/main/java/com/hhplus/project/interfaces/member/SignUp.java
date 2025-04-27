package com.hhplus.project.interfaces.member;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUp() {

    public record Request(
            @Schema(description = "사용자 이름", example = "김갑수")
            String name,

            @Schema(description = "이메일", example = "kabsukim@naver.com")
            String email,

            @Schema(description = "닉네임", example = "kapsoo0428")
            String nick
            ){}

    public record Response(
            @Schema(description = "사용자 식별자", example = "101")
            Long  eventId
    ){
        public static Response signUpSuccess(){ return new Response(101L);}
    }
}
