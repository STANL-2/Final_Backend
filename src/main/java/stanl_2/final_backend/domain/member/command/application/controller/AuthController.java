package stanl_2.final_backend.domain.member.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.member.command.application.dto.SigninRequestDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninResponseDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.common.response.ResponseMessage;

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
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody SignupDTO signupDTO){

        authCommandService.signup(signupDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("signin")
    public ResponseEntity<ResponseMessage> signin(@RequestBody SigninRequestDTO signinRequestDTO){

        // 로그인 서비스 호출하여 Access Token과 Refresh Token 발급
        SigninResponseDTO response = authCommandService.signin(signinRequestDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(response)
                                                .build());
    }


}
