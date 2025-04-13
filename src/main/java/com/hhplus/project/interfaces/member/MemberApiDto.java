package com.hhplus.project.interfaces.member;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

public interface MemberApiDto {

    record SignUpRequest(String userName, String email) {
    }

    record SignUpResponse(Long userId) {
    }


    record ModifyRequest(Optional<String> name, Optional<String> profileUrl) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record ModifyResponse(String name, String profileUrl) {
        public static ModifyResponse nameModified(String name) {
            return new ModifyResponse(name, null);
        }

        public static ModifyResponse profileModified(String profileUrl) {
            return new ModifyResponse(null, profileUrl);
        }
    }
}
