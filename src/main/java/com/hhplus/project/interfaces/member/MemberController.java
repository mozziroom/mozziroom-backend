package com.hhplus.project.interfaces.member;

import com.hhplus.project.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    @PostMapping("")
    public ResponseEntity<ApiResponse<MemberApiDto.SignUpResponse>> signUp(@RequestBody MemberApiDto.SignUpRequest request) {
        return new ResponseEntity<>(
                ApiResponse.of(
                        HttpStatus.CREATED,
                        "SignUp Complete",
                        new MemberApiDto.SignUpResponse(1L)), HttpStatus.CREATED);

    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse<MemberApiDto.ModifyResponse>> modify(@RequestBody MemberApiDto.ModifyRequest request) {
        return new ResponseEntity<>(
                ApiResponse.of(
                        HttpStatus.OK,
                        "Modify Success",
                        MemberApiDto.ModifyResponse.nameModified("김연습")), HttpStatus.OK);

    }
}
