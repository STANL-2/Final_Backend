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
    private String sessionId;
    private String userAgent;
    private String ipAddress;
    private String hostName;
    private Integer remotePort;
    private String uri;
    private String method;
    private String queryString;
    private String requestTime;
    private String transactionId;
    private String status;
    private String errorMessage;

    public LogSearchDTO(String logId,
                        String ipAddress,
                        String requestTime,
                        String status,
                        String method,
                        String uri){
        this.logId = logId;
        this.ipAddress = ipAddress;
        this.requestTime = requestTime;
        this.status = status;
        this.method = method;
        this.uri = uri;
    }
}
