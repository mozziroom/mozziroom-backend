package com.hhplus.project.interfaces.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    @Schema(description = "회원이름", example = "허재")
    String username;
    @Schema(description = "닉네임", example = "갓재")
    String nickname;
    @Schema(description = "프로필 사진 URL")
    String imageUrl;
}
