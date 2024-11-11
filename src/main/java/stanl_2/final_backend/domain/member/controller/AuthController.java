package stanl_2.final_backend.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.member.aggregate.dto.SignUpMemberDTO;
import stanl_2.final_backend.domain.member.aggregate.dto.jwtDTO;
import stanl_2.final_backend.domain.member.aggregate.vo.request.LoginRequestVO;
import stanl_2.final_backend.domain.member.aggregate.vo.request.SignUpRequestVO;
import stanl_2.final_backend.domain.member.service.MemberService;
import stanl_2.final_backend.global.common.response.ResponseMessage;
import stanl_2.final_backend.global.exception.CommonException;
import stanl_2.final_backend.global.exception.ErrorCode;
import stanl_2.final_backend.global.utils.RequestUtils;

@Slf4j
@RequiredArgsConstructor
@RestController("value = authController")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestVO signUpRequestVO) {

        SignUpMemberDTO memberRequestDTO = modelMapper.map(signUpRequestVO, SignUpMemberDTO.class);

        if (memberService.signUp(memberRequestDTO)) {
            new CommonException(ErrorCode.REGISTER_FAIL);
        }

//        return ResponseEntity.ok("회원가입 성공!");
        return ResponseEntity.ok(new ResponseMessage(200, "회원가입 성공", null));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인 API")
    public ResponseEntity<?> signin(@RequestBody LoginRequestVO loginRequestVO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        log.info("c1");
        // 인증 생성
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequestVO.getLoginId(), loginRequestVO.getPassword());

        log.info("c2");
        // 인증 처리
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        log.info("c3");

        // JWT 생성
        String jwt = memberService.signin(authenticationResponse);
        log.info("c4");

        if("".equals(jwt)){
            throw new CommonException(ErrorCode.LOGIN_FAILURE);
        }
        log.info("c5");

        // 로그인 이력 저장(ip, local 컴퓨터)
        log.info("{}", RequestUtils.getClientIp(request));
        log.info("{}", request.getHeader("User-Agent"));
        jwtDTO jwt1 = new jwtDTO();
        jwt1.setJwt(jwt);
//        return ResponseEntity.ok(jwt1);
        return ResponseEntity.ok(new ResponseMessage(200, "로그인 성공", jwt));
    }

}
