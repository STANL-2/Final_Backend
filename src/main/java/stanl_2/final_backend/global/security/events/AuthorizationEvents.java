package stanl_2.final_backend.global.security.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

@Component
@Slf4j
public class AuthorizationEvents {
    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent){
        log.error("권한 없음 유저: {} due to: {}", deniedEvent.getAuthentication().get().getName()
                , deniedEvent.getAuthorizationDecision().toString());
        throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
    }
}
