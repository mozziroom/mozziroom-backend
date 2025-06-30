package com.hhplus.project.interfaces.auth.dto;

public record Reissue() {
    public record Response(
            String accessToken
    ) {
        public static Reissue.Response from(com.hhplus.project.domain.auth.dto.Reissue.Info info) {
            return new Reissue.Response(info.accessToken());
        }
    }
}
