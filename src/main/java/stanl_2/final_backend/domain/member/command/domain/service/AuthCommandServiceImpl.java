package stanl_2.final_backend.domain.member.command.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
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
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.command.application.dto.*;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.MemberRole;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRoleRepository;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;
import stanl_2.final_backend.global.mail.MailService;
import stanl_2.final_backend.global.redis.RedisService;
import stanl_2.final_backend.global.security.service.MemberDetails;
import stanl_2.final_backend.global.utils.AESUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service("commandAuthService")
public class AuthCommandServiceImpl implements AuthCommandService {

    private final RedisService redisService;
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+<>?";

    // 임시 비밀번호의 최소 길이
    private static final int MIN_LENGTH = 12;

    private SecureRandom secureRandom = new SecureRandom();

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthQueryService authQueryService;
    private final AESUtils aesUtils;
    private final S3FileService s3FileService;
    private final MailService mailService;

    @Autowired
    public AuthCommandServiceImpl(MemberRepository memberRepository,
                                  MemberRoleRepository memberRoleRepository,
                                  PasswordEncoder passwordEncoder,
                                  ModelMapper modelMapper,
                                  AuthenticationManager authenticationManager,
                                  AuthQueryService authQueryService,
                                  AESUtils aesUtils,
                                  S3FileService s3FileService,
                                  MailService mailService, RedisService redisService) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.authQueryService = authQueryService;
        this.aesUtils = aesUtils;
        this.s3FileService = s3FileService;
        this.mailService = mailService;
        this.redisService = redisService;
    }

    @Override
    @Transactional
    public void signup(SignupDTO signupDTO, MultipartFile imageUrl) throws GeneralSecurityException {

        // 이미지 업로드 및 암호화
        signupDTO.setImageUrl(aesUtils.encrypt(s3FileService.uploadOneFile(imageUrl)));

        String hashPwd = passwordEncoder.encode(signupDTO.getPassword());
        signupDTO.setPassword(hashPwd);
        signupDTO.setName(aesUtils.encrypt(signupDTO.getName()));
        signupDTO.setEmail(aesUtils.encrypt(signupDTO.getEmail()));
        signupDTO.setIdentNo(aesUtils.encrypt(signupDTO.getIdentNo()));
        signupDTO.setPhone(aesUtils.encrypt(signupDTO.getPhone()));
        signupDTO.setEmergePhone(aesUtils.encrypt(signupDTO.getEmergePhone()));
        signupDTO.setAddress(aesUtils.encrypt(signupDTO.getAddress()));
        signupDTO.setBankName(aesUtils.encrypt(signupDTO.getBankName()));
        signupDTO.setAccount(aesUtils.encrypt(signupDTO.getAccount()));

        Member registerMember = modelMapper.map(signupDTO, Member.class);

        memberRepository.save(registerMember);
    }

    @Override
    @Transactional
    public SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) throws GeneralSecurityException {
        // 사용자 인증
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                signinRequestDTO.getLoginId(), signinRequestDTO.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse == null || !authenticationResponse.isAuthenticated()) {
            throw new GlobalCommonException(GlobalErrorCode.LOGIN_FAILURE);
        }

        // 인증된 사용자 정보를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        // 권한 정보 추출
        String authorities = authenticationResponse.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // JWT 토큰 생성
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
        String accessToken = generateAccessToken(authenticationResponse.getName(), authorities, secretKey);
        String refreshToken = generateRefreshToken(authenticationResponse.getName(), authorities,secretKey);

        MemberDetails memberDetails = (MemberDetails) authenticationResponse.getPrincipal();

        String memberAuthorities = memberDetails.getAuthorities().toString();
        memberAuthorities = memberAuthorities.substring(1, memberAuthorities.length() - 1);
        String[] roleArray = memberAuthorities.split(",\\s*");
        String firstRole = "";
        if (roleArray.length > 0) {
            firstRole = roleArray[0].replace("ROLE_", ""); // ROLE_ 제거
        } else {
            throw new GlobalCommonException(GlobalErrorCode.AUTHORITIES_NOT_FOUND);
        }
        return new SigninResponseDTO(
                accessToken, refreshToken,
                aesUtils.decrypt(memberDetails.getMember().getName()),
                memberDetails.getMember().getPosition(),
                firstRole,
                aesUtils.decrypt(memberDetails.getMember().getImageUrl())
        );
    }

    @Override
    @Transactional
    public void sendEmail(CheckMailDTO checkMailDTO) throws GeneralSecurityException, MessagingException {
        String email = authQueryService.findEmail(checkMailDTO.getLoginId());

        mailService.sendEmail(email);
    }

    @Override
    @Transactional
    public void checkNum(CheckNumDTO checkNumDTO) throws GeneralSecurityException {
        String email = authQueryService.findEmail(checkNumDTO.getLoginId());

        if (checkNumDTO.getNumber() != null && !checkNumDTO.getNumber().equals(redisService.getKey(email))) {
            throw new MemberCommonException(MemberErrorCode.NUMBER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void sendNewPwd(String loginId) throws MessagingException, GeneralSecurityException {
        StringBuilder password = new StringBuilder();

        // 반드시 포함할 문자들
        password.append(randomChar(LOWERCASE));  // 소문자
        password.append(randomChar(UPPERCASE));  // 대문자
        password.append(randomChar(DIGITS));     // 숫자
        password.append(randomChar(SPECIAL_CHARACTERS)); // 특수문자

        // 나머지 비밀번호 길이 채우기 (MIN_LENGTH 까지)
        String allChars = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;
        for (int i = password.length(); i < MIN_LENGTH; i++) {
            password.append(randomChar(allChars));
        }

        String hashPwd = passwordEncoder.encode(password);

        Member newPwdMem = memberRepository.findByLoginId(loginId);

        mailService.sendPwdEmail(aesUtils.decrypt(newPwdMem.getEmail()), password);

        newPwdMem.setPassword(hashPwd);

        memberRepository.save(newPwdMem);
    }

    private char randomChar(String charSet) {
        int randomIndex = secureRandom.nextInt(charSet.length());
        return charSet.charAt(randomIndex);
    }

    private String generateAccessToken(String username, String authorities, SecretKey secretKey) {
        return Jwts.builder()
                .setIssuer("STANL2")
                .setSubject("Access Token")
                .claim("username", username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) // 30분 유효
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken(String username, String authorities, SecretKey secretKey) {
        return Jwts.builder()
                .setIssuer("STANL2")
                .setSubject("Refresh Token")
                .claim("username", username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24시간 유효
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

        // 사용자 정보 추출
        String username = claims.get("username", String.class);
        String authorities = claims.get("authorities", String.class);

        if (username == null || authorities == null) {
            throw new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR);
        }

        // 새로운 Access Token 생성
        return RefreshDTO.builder()
                .newAccessToken(generateAccessToken(username, authorities, secretKey))
                .build();
    }

    @Override
    @Transactional
    public void grantAuthority(GrantDTO grantDTO) {

        String loginId = authQueryService.selectMemberIdByLoginId(grantDTO.getLoginId());

        MemberRole newMemberRole = modelMapper.map(grantDTO, MemberRole.class);

        // fk 값 설정
        newMemberRole.setMemberId(loginId);

        memberRoleRepository.save(newMemberRole);
    }
}
