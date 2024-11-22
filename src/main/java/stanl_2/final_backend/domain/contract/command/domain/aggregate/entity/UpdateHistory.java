package stanl_2.final_backend.domain.contract.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_UPDATE_HISTORY")
public class UpdateHistory {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "UPD_HIS")
    )
    @Column(name = "UPD_ID", nullable = false)
    private String updateHistoryId;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "UPD_CONT", nullable = false)
    private String content;

    @Column(name = "CONR_ID", nullable = false)
    private String contractId;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
