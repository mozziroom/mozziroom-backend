package com.hhplus.project.interfaces.member;

import com.hhplus.project.support.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    @Operation(summary = "사용자 회원 가입", description = "회원 정보를 생성 합니다.")
    @PostMapping("")
    public ApiResponse<SignUp.Response> signUp(@Parameter(description = "회원 가입하는 사용자 정보")  @RequestBody SignUp.Request request) {
        return ApiResponse.ok(SignUp.Response.signUpSuccess());
    }


    @Operation(summary = "사용자 정보 수정", description = "회원이 자신의 정보를 수정 합니다.")
    @PatchMapping("/{memberId}")
    public ApiResponse<Void> update(
            @Parameter(description = "수정할 사용자 Id") @PathVariable Long memberId,
            @Parameter(description = "수정 해야할 사용자 정보") @RequestBody Update.Request request) {
        return ApiResponse.ok();
    }
}
