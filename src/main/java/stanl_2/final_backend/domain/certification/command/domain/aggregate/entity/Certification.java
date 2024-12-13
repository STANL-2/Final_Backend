package stanl_2.final_backend.domain.certification.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "TB_CERTIFICATION")
public class Certification {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CER")
    )
    @Column(name = "CER_ID", nullable = false)
    private String certificationId;

    @Column(name = "CER_NAME", nullable = false)
    private String name;

    @Column(name = "CER_INST", nullable = false)
    private String agency;

    @Column(name = "CER_DATE", nullable = false)
    private String acquisitionDate;

    @Column(name = "CER_SCO", nullable = false)
    private String score;

    @Column(name = "CER_NOTE")
    private String note;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    // Insert 되기 전에 실행
    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
