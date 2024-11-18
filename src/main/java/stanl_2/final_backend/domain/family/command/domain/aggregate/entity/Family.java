package stanl_2.final_backend.domain.family.command.domain.aggregate.entity;

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
@Table(name = "tb_family")
public class Family {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "FAM")
    )
    @Column(name = "FAM_ID", nullable = false)
    private String familyId;

    @Column(name = "FAM_NAME", nullable = false)
    private String name;

    @Column(name = "FAM_REL", nullable = false)
    private String relation;

    @Column(name = "FAM_BIR", nullable = false)
    private String birth;

    @Column(name = "FAM_IDEN_NO", nullable = false)
    private String identNo;

    @Column(name = "FAM_PHO", nullable = false)
    private String phone;

    @Column(name = "FAM_SEX", nullable = false)
    private String sex;

    @Column(name = "FAM_DIS", nullable = false)
    private Boolean disability;

    @Column(name = "FAM_DIE", nullable = false)
    private Boolean die;

    @Column(name = "FAM_NOTE")
    private String note;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String  createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String  updatedAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memId;

    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = getCurrentTime();
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
