package stanl_2.final_backend.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stanl_2.final_backend.domain.member.aggregate.entity.Member;

import java.util.Collection;
import java.util.Collections;

// 사용자 인증 시 필요한 정보 제공
@RequiredArgsConstructor
public class MemberPrincipal implements UserDetails {

    private final Member member;

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 비즈니스 로직에 따라 설정 (true로 설정하면 계정 만료 미사용)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 비즈니스 로직에 따라 설정 (true로 설정하면 계정 잠금 미사용)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 비즈니스 로직에 따라 설정 (true로 설정하면 자격 증명 만료 미사용)
    }

//    @Override
//    public boolean isEnabled() {
//        return member.isEnabled();  // 활성화 상태에 따라 true/false 반환
//    }
}
