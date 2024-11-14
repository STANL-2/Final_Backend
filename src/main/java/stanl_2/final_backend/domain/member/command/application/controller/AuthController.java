package stanl_2.final_backend.domain.member.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninRequestDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninResponseDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;

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
    public ResponseEntity<MemberResponseMessage> signup(@RequestBody SignupDTO signupDTO){

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


    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @PostMapping("signin")
    public ResponseEntity<MemberResponseMessage> signin(@RequestBody SigninRequestDTO signinRequestDTO,
                                                        HttpServletResponse response){

        // 로그인 서비스 호출하여 Access Token과 Refresh Token 발급
        SigninResponseDTO signinResponseDTO = authCommandService.signin(signinRequestDTO);


        // CSRF 토큰 생성 및 쿠키에 추가
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        CsrfToken csrfToken = (CsrfToken) attr.getRequest().getAttribute(CsrfToken.class.getName());

        if (csrfToken != null) {
            Cookie csrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
            csrfCookie.setPath("/");
            csrfCookie.setHttpOnly(false);
            csrfCookie.setSecure(attr.getRequest().isSecure());
            csrfCookie.setMaxAge(-1); // 세션 종료 시 삭제
            response.addCookie(csrfCookie);
        }


        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(signinResponseDTO)
                                                .build());
    }


}
