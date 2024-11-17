package stanl_2.final_backend.domain.member.command.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

//    @GetMapping("/authorities")
//    public ResponseEntity<MemberResponseMessage> check(Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(MemberResponseMessage.builder()
//                            .httpStatus(401)
//                            .msg("Unauthorized")
//                            .build());
//        }
//
//        // 인증된 사용자 정보 출력
//        log.info("인증된 사용자: {}", authentication.getName());
//
//        return ResponseEntity.ok(MemberResponseMessage.builder()
//                .httpStatus(200)
//                .msg("성공")
//                .result("인증된 사용자: " + authentication.getName())
//                .build());
//    }


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
