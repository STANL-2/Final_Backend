package stanl_2.final_backend.global.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;

import java.util.Collection;
import java.util.stream.Collectors;

public class MemberDetails implements UserDetails {

    private final Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toList());
    }

    public String getPosition() {
        return member.getPosition();
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
        return member.getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return member.getActive();
    }

    @Override
    public boolean isEnabled() {
        return member.getActive();
    }

}
