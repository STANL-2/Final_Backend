package stanl_2.final_backend.global.security.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Getter
public class ApplicationConstants {

    private final byte[] JWT_SECRET_KEY;
    private final String JWT_HEADER;

    public ApplicationConstants(
            @Value("${jwt.secret-default-value}") String jwtSecretDefaultValue,
            @Value("${jwt.header}") String jwtHeader
    ) {
        this.JWT_SECRET_KEY = Base64.getDecoder().decode(jwtSecretDefaultValue);
        this.JWT_HEADER = jwtHeader;
    }
}