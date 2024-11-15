package stanl_2.final_backend.global.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;
import stanl_2.final_backend.global.exception.GlobalExceptionResponse;

@Slf4j
@Component
@Profile("prod")
public class ProdUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProdUsernamePwdAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            log.error("UserDetails is null for username: {}", username);
            throw new GlobalCommonException(GlobalErrorCode.USER_NOT_FOUND);
        }

        if(passwordEncoder.matches(pwd, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, pwd, userDetails.getAuthorities());
        } else {
            throw new GlobalCommonException(GlobalErrorCode.LOGIN_FAILURE);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
