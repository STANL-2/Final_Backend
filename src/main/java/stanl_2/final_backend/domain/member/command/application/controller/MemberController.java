package stanl_2.final_backend.domain.member.command.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    @GetMapping("authorities")
    public ResponseEntity<MemberResponseMessage> check(Principal principal) {
        if (principal == null || "anonymousUser".equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                    .body(MemberResponseMessage.builder()
                                                    .httpStatus(401)
                                                    .msg("Unauthorized")
                                                    .build());
        }

        return ResponseEntity.ok(MemberResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result("인증된 사용자: " + principal)
                                                .build());
    }
}
