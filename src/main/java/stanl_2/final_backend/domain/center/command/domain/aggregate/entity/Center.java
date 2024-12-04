package stanl_2.final_backend.domain.center.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="TB_CENTER")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Center {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CEN")
    )
    @Column(name ="CENT_ID")
    private String centerId;

    @Column(name = "CENT_NAME", nullable = false)
    private String name;

    @Column(name = "CENT_ADR", nullable = false)
    private String address;

    @Column(name = "CENT_PHO", nullable = false)
    private String phone;

    @Column(name = "CENT_MEM_CNT", nullable = false)
    private Integer memberCount;

    @Column(name = "CENT_OPR_AT", nullable = false)
    private String operatingAt;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = getCurrentTime();
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


}
