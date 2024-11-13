package stanl_2.final_backend.domain.member.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.member.command.application.service.MemberCommandService;
import stanl_2.final_backend.domain.member.common.response.ResponseMessage;
import stanl_2.final_backend.global.security.service.MemberDetails;

@RestController("commandMemberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Autowired
    public MemberController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }


    @GetMapping("check")
    public ResponseEntity<ResponseMessage> check(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseMessage.builder()
                            .httpStatus(401)
                            .msg("Unauthorized")
                            .build());
        }
        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(memberDetails.getMember())
                .build());
    }
}
