package stanl_2.final_backend.domain.member.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.member.aggregate.dto.SignUpMemberDTO;
import stanl_2.final_backend.domain.member.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.repository.MemberRepository;
import stanl_2.final_backend.global.security.constants.ApplicationConstants;
import stanl_2.final_backend.global.security.service.MemberPrincipal;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Slf4j
@Service("memberServiceImpl")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationConstants applicationConstants;
    private static final long JWT_EXPIRATION_TIME = 30000000L;

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Override
    public Boolean signUp(SignUpMemberDTO memberRequestDTO) {
        Timestamp currentTimestamp = getCurrentTimestamp();

        String hashPwd = passwordEncoder.encode(memberRequestDTO.getPassword());
        memberRequestDTO.setPassword(hashPwd);

        Member signUpMember = modelMapper.map(memberRequestDTO, Member.class);
        signUpMember.setCreatedAt(currentTimestamp);
        signUpMember.setUpdatedAt(currentTimestamp);

        Member newMember = memberRepository.save(signUpMember);

        if(newMember != null) {
            return false;
        }

        return true;
    }

    @Override
    public String signin(Authentication authenticationResponse) {

        String jwt = "";

        if(authenticationResponse != null && authenticationResponse.isAuthenticated()){

            // Base64로 디코딩된 SecretKey 사용
            byte[] decodedKey = applicationConstants.getJWT_SECRET_KEY();
            SecretKey secretKey = Keys.hmacShaKeyFor(decodedKey);

            MemberPrincipal memberDetails = (MemberPrincipal) authenticationResponse.getPrincipal();
            Member member = memberDetails.getMember();  // MemberPrincipal에서 Member를 얻어옴

            jwt = Jwts.builder()
                    .setIssuer("STANL2")
                    .setSubject("JWT Token")
                    .claim("loginId", authenticationResponse.getName())
                    .claim("authorities", authenticationResponse.getAuthorities()
                    .stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .setIssuedAt(new java.util.Date())
                    .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 30000000))
                    .signWith(secretKey).compact();
        }
        return jwt;
    }
}
