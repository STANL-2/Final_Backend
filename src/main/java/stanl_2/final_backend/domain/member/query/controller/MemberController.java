package stanl_2.final_backend.domain.member.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;

@RestController(value = "queryMemberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Operation(summary = "회원 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<MemberResponseMessage> getMemberInfo(Principal principal) throws GeneralSecurityException {

        MemberDTO memberInfo = memberQueryService.selectMemberInfo(principal.getName());

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(memberInfo)
                                                        .build());
    }




}
