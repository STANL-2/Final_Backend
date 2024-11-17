package stanl_2.final_backend.global.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

@Slf4j
@Service(value = "MemberDetailsService")
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // JPA를 사용하여 로그인 ID로 회원 정보 조회
        Member member = memberRepository.findByLoginId(username);
        if (member == null) {
            throw new GlobalCommonException(GlobalErrorCode.USER_NOT_FOUND);
        }

        // Member를 기반으로 UserDetails 생성
        return new MemberDetails(member);
    }
}
