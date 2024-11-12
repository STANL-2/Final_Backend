package stanl_2.final_backend.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 사용자 정보 호출
@Slf4j
@Service(value="memberDetailsServiceImpl")
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements MemberDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByloginId(loginId);
        if(member == null) {
            log.info("D1");
            throw new UsernameNotFoundException(loginId);
        }
        return new MemberPrincipal(member);
    }
}
