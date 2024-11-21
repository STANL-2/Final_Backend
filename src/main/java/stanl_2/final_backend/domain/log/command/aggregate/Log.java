package stanl_2.final_backend.domain.log.command.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_log")
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

    @Column(name = "USER_AGENT", length = 500)
    private String userAgent;

    @Column(name = "IP_ADDRESS", length = 45)
    private String ipAddress;

    @Column(name = "HOST_NAME")
    private String hostName;

    @Column(name = "REMOTE_PORT")
    private Integer remotePort;

    @Column(name = "URI", length = 2048)
    private String uri;

    @Column(name = "METHOD")
    private String method;

    @Column(name = "QUERY_STRING", length = 2048)
    private String queryString;

    @Column(name = "REQUEST_TIME", nullable = false, updatable = false)
    private String requestTime;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "STATUS", nullable = false, length = 50)
    private String status;

    @Column(name = "ERROR_MESSAGE", columnDefinition = "TEXT")
    private String errorMessage;


    @PrePersist
    private void prePersist() {
        this.requestTime = getCurrentTime();
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}


