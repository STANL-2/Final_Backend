package stanl_2.final_backend.domain.member.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.member.command.application.dto.*;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;

import java.security.GeneralSecurityException;

@Slf4j
@RestController("commandAuthController")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthCommandService authCommandService;

    @Autowired
    public AuthController(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("signup")
    public ResponseEntity<MemberResponseMessage> signup(@RequestBody SignupDTO signupDTO) throws GeneralSecurityException {

        authCommandService.signup(signupDTO);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }

    @Operation(summary = "권한 부여")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<MemberResponseMessage> grantAuthority(@RequestBody GrantDTO grantDTO){

        authCommandService.grantAuthority(grantDTO);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }


    @PostMapping("signin")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    public ResponseEntity<MemberResponseMessage> signin(@RequestBody SigninRequestDTO signinRequestDTO) {

        SigninResponseDTO responseDTO = authCommandService.signin(signinRequestDTO);

        return ResponseEntity.ok(
                MemberResponseMessage.builder()
                        .httpStatus(200)
                        .msg("성공")
                        .result(responseDTO)
                        .build()
        );
    }

    @Operation(summary = "토큰 갱신")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("refresh")
    public ResponseEntity<MemberResponseMessage> refresh(@RequestBody RefreshDTO refreshDTO) {

        RefreshDTO newAccessToken = authCommandService.refreshAccessToken(refreshDTO.getRefreshToken());

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(newAccessToken)
                .build());
    }


}
