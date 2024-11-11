package stanl_2.final_backend.domain.center.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/* 설명. 테스트를 위한 어노테이션(나중에 삭제 예정)*/
@ToString

@Entity
@Table(name="CENTER")
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
    private String id;

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
    private Timestamp createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "DELETED_AT")
    private Timestamp deletedAt;

    @Column(name = "ACTIVE", nullable = false)
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
    public void preUpdate() {
        this.updatedAt = getCurrentTimestamp();
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

}
