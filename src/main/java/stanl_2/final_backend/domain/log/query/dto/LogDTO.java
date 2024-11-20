package stanl_2.final_backend.domain.log.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LogDTO {

    private Long logId;

    // User Information
    private String sessionId;
    private String userAgent;

    // Network Information
    private String ipAddress;
    private String hostName;
    private Integer remotePort;

    // Request Information
    private String uri;
    private String method;
    private String queryString;

    // Time Information
    private LocalDateTime requestTime;

    // Additional Information
    private String transactionId;
}
