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
import java.time.format.DateTimeFormatter;

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
    private String  createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String  updatedAt;

    @Column(name = "DELETED_AT")
    private String  deletedAt;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @Column(name = "IMAGE_URL")
    private String imageUrl;


    /* 설명. updatedAt 자동화 */
    // Insert 되기 전에 실행
    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
        this.updatedAt = this.createdAt;
    }

    // Update 되기 전에 실행
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = getCurrentTime();
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
