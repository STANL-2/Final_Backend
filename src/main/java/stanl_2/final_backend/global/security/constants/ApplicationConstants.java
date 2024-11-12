package stanl_2.final_backend.global.security.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class ApplicationConstants {

    // 비밀키는 자동으로 생성하여 상수로 설정
    public final String JWT_SECRET_KEY = "JWT_SECRET";

    private final String JWT_SECRET_DEFAULT_VALUE="jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";

    public final String JWT_HEADER = "Authorization";
}
