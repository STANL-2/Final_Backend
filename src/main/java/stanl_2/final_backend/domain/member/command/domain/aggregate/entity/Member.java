package stanl_2.final_backend.domain.member.command.domain.aggregate.entity;

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
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "MEM")
    )
    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    @Column(name = "MEM_LOGIN_ID", nullable = false, unique = true)
    private String loginId;

    @Column(name = "MEM_PWD", nullable = false)
    private String password;

    @Column(name = "MEM_NAME", nullable = false)
    private String name;

    @Column(name = "MEM_IMAGEURL", nullable = false)
    private String imageUrl;

    @Column(name = "MEM_EMA", nullable = false)
    private String email;

    @Column(name = "MEM_AGE", nullable = false)
    private Integer age;

    @Column(name = "MEM_SEX", nullable = false)
    private String sex;

    @Column(name = "MEM_IDEN_NO", nullable = false)
    private String identNo;

    @Column(name = "MEM_PHO", nullable = false)
    private String phone;

    @Column(name = "MEM_EMER_PHO")
    private String emergePhone;

    @Column(name = "MEM_ADR", nullable = false)
    private String address;

    @Column(name = "MEM_NOTE")
    private String note;

    @Column(name = "MEM_POS", nullable = false)
    private String position;

    @Column(name = "MEM_GRD", nullable = false)
    private String grade;

    @Column(name = "MEM_JOB_TYPE", nullable = false)
    private String jobType;

    @Column(name = "MEM_MIL", nullable = false)
    private String military;

    @Column(name = "MEM_BANK_NAME")
    private String bankName;

    @Column(name = "MEM_ACC")
    private String account;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "CENTER_ID", nullable = false)
    private String centerId;

    @Column(name = "ORG_CHA_ID", nullable = false)
    private String organizationId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEM_ID")
    private List<MemberRole> roles = new ArrayList<>();


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
