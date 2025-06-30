package com.hhplus.project.domain.auth.dto;

public class Issue {
    public record Info(
            String accessToken,
            String refreshToken
    ) {

    }
}
