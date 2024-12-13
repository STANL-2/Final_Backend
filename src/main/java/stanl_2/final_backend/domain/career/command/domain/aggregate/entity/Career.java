package stanl_2.final_backend.domain.career.command.domain.aggregate.entity;

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
@Table(name = "tb_CAREER")
public class Career {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CAR")
    )
    @Column(name = "CAR_ID", nullable = false)
    private String careerId;

    @Column(name = "CAR_EMP_DATE", nullable = false)
    private String emplDate;

    @Column(name = "CAR_RTR_DATE")
    private String resignDate;

    @Column(name = "CAR_NAME", nullable = false)
    private String name;

    @Column(name = "CAR_NOTE")
    private String note;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String  createdAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;


    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
