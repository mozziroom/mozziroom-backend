package com.hhplus.project.fixture;

import com.hhplus.project.support.security.jwt.TokenProvider;

public class TokenProviderFixture {

    private static final String ACCESS_SECRET = "cdcq0Qt7yrsHkvI1uN2wA9nE1kBm4c5s0WCbaiHS3nY=";
    private static final String REFRESH_SECRET = "vTmdmU+aQ6U14Psw0uAsgBi/TC8+6uk3Wc/kPnfTjSI=";

    public static TokenProvider create(
            long accessExpiration,
            long refreshExpiration
    ) {
        return new TokenProvider(
                ACCESS_SECRET,
                accessExpiration,
                REFRESH_SECRET,
                refreshExpiration
        );
    }
}
