package stanl_2.final_backend.domain.member.command.domain.service;

import io.jsonwebtoken.Claims;
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
import stanl_2.final_backend.domain.member.command.application.dto.*;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.MemberRole;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRoleRepository;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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
        // 사용자 인증
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                signinRequestDTO.getLoginId(), signinRequestDTO.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse == null || !authenticationResponse.isAuthenticated()) {
            throw new GlobalCommonException(GlobalErrorCode.LOGIN_FAILURE);
        }

        // 인증된 사용자 정보를 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        // 권한 정보 추출
        String authorities = authenticationResponse.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // JWT 토큰 생성
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
        String accessToken = generateAccessToken(authenticationResponse.getName(), authorities, secretKey);
        String refreshToken = generateRefreshToken(secretKey);

        return new SigninResponseDTO(accessToken, refreshToken);
    }

    private String generateAccessToken(String username, String authorities, SecretKey secretKey) {
        return Jwts.builder()
                .setIssuer("STANL2")
                .setSubject("Access Token")
                .claim("username", username)
                .claim("authorities", authorities) // 권한 정보를 클레임에 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1시간 유효
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken(SecretKey secretKey) {
        return Jwts.builder()
                .setIssuer("STANL2")
                .setSubject("Refresh Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // 7일 유효
                .signWith(secretKey)
                .compact();
    }

    @Override
    public RefreshDTO refreshAccessToken(String refreshToken) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));


        // Refresh Token을 검증하고 클레임을 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        // 토큰의 주제가 "Refresh Token"인지 확인
        if (!"Refresh Token".equals(claims.getSubject())) {
            throw new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR);
        }

        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
                throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }

        // 새로운 Access Token 생성 및 반환
        return RefreshDTO.builder()
                .newAccessToken(
                        generateAccessToken(authentication.getName(),
                                authentication.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.joining(",")),
                                secretKey)
                )
                .build();
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
