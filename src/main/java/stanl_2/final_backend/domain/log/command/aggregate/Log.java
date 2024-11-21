package stanl_2.final_backend.domain.log.command.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Log {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "LOG")
    )
    @Column(name = "LOG_ID", nullable = false)
    private String logId;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @Column(name = "USER_AGENT")
    private String userAgent;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "HOST_NAME")
    private String hostName;

    @Column(name = "REMOTE_PORT")
    private Integer remotePort;

    @Column(name = "URI")
    private String uri;

    @Column(name = "METHOD")
    private String method;

    @Column(name = "QUERY_STRING")
    private String queryString;

    @Column(name = "REQUEST_TIME")
    private LocalDateTime requestTime;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;
}


