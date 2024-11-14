package stanl_2.final_backend.domain.member.command.domain.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninRequestDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninResponseDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.MemberRole;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRoleRepository;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.global.exception.CommonException;
import stanl_2.final_backend.global.exception.ErrorCode;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Slf4j
@Service("commandAuthService")
public class AuthCommandServiceImpl implements AuthCommandService {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthQueryService authQueryService;

    @Autowired
    public AuthCommandServiceImpl(MemberRepository memberRepository,
                                  MemberRoleRepository memberRoleRepository,
                                  PasswordEncoder passwordEncoder,
                                  ModelMapper modelMapper,
                                  AuthenticationManager authenticationManager,
                                  AuthQueryService authQueryService) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.authQueryService = authQueryService;
    }

    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void signup(SignupDTO signupDTO) {

        String hashPwd = passwordEncoder.encode(signupDTO.getPassword());
        signupDTO.setPassword(hashPwd);

        Member registerMember = modelMapper.map(signupDTO, Member.class);

        memberRepository.save(registerMember);
    }


    @Override
    @Transactional
    public SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) {

        // 사용자 인증을 위한 Authentication 객체 생성
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(signinRequestDTO.getLoginId(), signinRequestDTO.getPassword());

        // AuthenticationManager를 사용해 인증 처리
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse == null || !authenticationResponse.isAuthenticated()) {
            throw new CommonException(ErrorCode.LOGIN_FAILURE);
        }

        // 인증된 사용자 정보를 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        // 사용자 정보 가져오기
//        MemberDetails memberDetails = (MemberDetails) authenticationResponse.getPrincipal(); // 수정된 부분
//        Member member = memberDetails.getMember();  // MemberDetails에서 Member를 얻어옴
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        // Access Token (1시간)
        String accessToken = Jwts.builder()
                .setIssuer("STANL2")
                .setSubject("Access Token")
//                .claim("id", member.getId()) // `id`만 포함
                .claim("username", signinRequestDTO.getLoginId())
                .claim("authorities", authenticationResponse.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 3600000)) // 1시간 유효
                .signWith(secretKey)
                .compact();

        // Refresh Token (7일)
        String refreshToken = Jwts.builder()
                .setIssuer("STANL2")
                .setSubject("Refresh Token")
//                .claim("id", member.getId())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 604800000)) // 7일 유효
                .signWith(secretKey)
                .compact();

        return new SigninResponseDTO(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void grantAuthority(GrantDTO grantDTO) {

        String id = authQueryService.selectMemberLoginId(grantDTO.getLoginId());

        MemberRole newMemberRole = modelMapper.map(grantDTO, MemberRole.class);

        // fk 값 설정
        newMemberRole.setMemberId(id);

        memberRoleRepository.save(newMemberRole);
    }


}
