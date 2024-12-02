package stanl_2.final_backend.domain.member.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.command.application.dto.*;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
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

    /**
     * 회원가입 @ResponseBody
     * {
     *     "loginId": "test",
     *     "password": "test",
     *     "name": "이름1",
     *     "email": "test@test.com",
     *     "age": 30,
     *     "sex": "MALE",
     *     "identNo": "12123",
     *     "phone": "01012345678",
     *     "emergePhone": "01088888888",
     *     "address": "서울",
     *     "note": "비고1",
     *     "position": "인턴",
     *     "grade": "고졸",
     *     "jobType": "영업",
     *     "military": "미필",
     *     "bankName": "국민은행",
     *     "account": "110-2324-131313-12232",
     *     "centerId": "CEN_000000001",
     *     "organizationId": "ORG_000000001"
     * }
     */
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("signup")
    public ResponseEntity<MemberResponseMessage> signup(@RequestPart("dto") SignupDTO signupDTO,
                                                        @RequestPart("file") MultipartFile imageUrl)
                                                        throws GeneralSecurityException {

        authCommandService.signup(signupDTO, imageUrl);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(null)
                                                      .build());
    }

    /**
     * 권한 부여 @ResponseBody
     * {
     *     "loginId": "test",
     *     "role": "ADMIN"
     * }
     */

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


    /**
     * 로그인 @ResponseBody
     * {
     *     "loginId": "test",
     *     "password": "test"
     * }
     */
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("signin")
    public ResponseEntity<MemberResponseMessage> signin(@RequestBody SigninRequestDTO signinRequestDTO) throws GeneralSecurityException {

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


    @Operation(summary = "임시 비밀번호 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("checkmail")
    public ResponseEntity<MemberResponseMessage> checkMail(@RequestBody CheckMailDTO checkMailDTO) throws GeneralSecurityException, MessagingException {

        authCommandService.sendEmail(checkMailDTO);

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(null)
                                                      .build());
    }


    @Operation(summary = "임시 비밀번호 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("checknum")
    public ResponseEntity<MemberResponseMessage> checkMail(@RequestBody CheckNumDTO checkNumDTO) throws GeneralSecurityException, MessagingException {

        authCommandService.checkNum(checkNumDTO);

        authCommandService.sendNewPwd(checkNumDTO.getLoginId());

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(null)
                                                      .build());
    }

}
