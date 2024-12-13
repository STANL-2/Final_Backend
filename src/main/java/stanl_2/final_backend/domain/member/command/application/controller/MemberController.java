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
import stanl_2.final_backend.domain.member.command.application.service.MemberCommandService;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;

import java.security.Principal;

@Slf4j
@RestController("commandMemberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Autowired
    public MemberController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }


    @Operation(summary = "본인정보(Principal) 출력 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))})
    })
    @GetMapping("/authorities")
    public ResponseEntity<MemberResponseMessage> check(Principal principal) {

        // 인증된 사용자 정보 출력
        log.info("인증된 사용자: {}", principal.getName());

        return ResponseEntity.ok(MemberResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result("인증된 사용자: " + principal.getName())
                .build());
    }




}
