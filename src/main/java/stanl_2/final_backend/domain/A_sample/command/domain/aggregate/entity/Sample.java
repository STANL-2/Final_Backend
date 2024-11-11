package stanl_2.final_backend.domain.A_sample.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SAMPLE")
public class Sample {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "SAM")
    )
    @Column(name = "SAM_ID")
    private String id;

    @Column(name = "SAM_NAME")
    private String name;

    @Column(name = "SAM_NUM")
    private Integer num;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "DELETED_AT")
    private Timestamp deletedAt;

    @Column(name = "ACTIVE")
    private Boolean active = true;


    /* 설명. updatedAt 자동화 */
    // Insert 되기 전에 실행
    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTimestamp();
        this.updatedAt = this.createdAt;
    }

    // Update 되기 전에 실행
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = getCurrentTimestamp();
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

}
