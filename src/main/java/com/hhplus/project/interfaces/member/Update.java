package com.hhplus.project.interfaces.member;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

public record Update() {

    public record Request(
            @Schema(description = "닉네임", example = "kapsoo0428")
            Optional<String> nick,

            @Schema(description = "프로필 URL", example = "/users/member/101/adfafadsfdasfadsf.jpg")
            Optional<String> profileUrl
    ){}

    public record Response(){}
}
