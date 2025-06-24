package com.hhplus.project.interfaces.auth.dto;

public record Reissue() {
    public record Response(
            String accessToken
    ) {

    }
}
