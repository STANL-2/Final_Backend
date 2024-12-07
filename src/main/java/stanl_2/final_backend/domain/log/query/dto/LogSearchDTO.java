package stanl_2.final_backend.domain.log.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogSearchDTO {
    private String logId;
    private String loginId;
    private String sessionId;
    private String userAgent;
    private String ipAddress;
    private String hostName;
    private Integer remotePort;
    private String uri;
    private String method;
    private String queryString;
    private String requestTime_start;
    private String requestTime_end;
    private String transactionId;
    private String status;
    private String errorMessage;

    public LogSearchDTO(String logId,
                        String loginId,
                        String ipAddress,
                        String requestTime_start,
                        String requestTime_end,
                        String status,
                        String method,
                        String uri){
        this.logId = logId;
        this.loginId = loginId;
        this.ipAddress = ipAddress;
        this.requestTime_start = requestTime_start;
        this.requestTime_end = requestTime_end;
        this.status = status;
        this.method = method;
        this.uri = uri;
    }
}
