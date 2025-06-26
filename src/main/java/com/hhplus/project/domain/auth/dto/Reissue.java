package com.hhplus.project.domain.auth.dto;

public record Reissue() {
    public record Info(
            String accessToken
    ) {

    }
}
