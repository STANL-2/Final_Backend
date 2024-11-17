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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_MEMBER_DETAIL")
public class MemberDetail {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "DET")
    )
    @Column(name = "MEM_DET_ID", nullable = false)
    private String memberDetailId;

    @Column(name = "MEM_DET_REL")
    private String memberDetailRelation;

    @Column(name = "MEM_DET_NAME")
    private String memberDetailName;

    @Column(name = "MEM_DET_BIR")
    private String memberDetailBirth;

    @Column(name = "MEM_DET_IDEN_NO")
    private String memberDetailIdentNo;

    @Column(name = "MEM_DET_PHO")
    private String memberDetailPhone;

    @Column(name = "MEM_DET_SEX")
    private String memberDetailSex;

    @Column(name = "MEM_DET_DIS")
    private Boolean memberDetailDisorder;

    @Column(name = "MEM_DET_DIE")
    private Boolean memberDetailDie;

    @Column(name = "MEM_DET_NOTE")
    private String memberDetailNote;

    @Column(name = "MEM_DET_ENTD", nullable = false)    //222
    private String memberDetailEntrance;

    @Column(name = "MEM_DET_GRAD", nullable = false)    //222
    private String memberDetailGraduate;

    @Column(name = "MEM_DET_FNL_EDC", nullable = false) //222
    private String memberDetailFinalEdu;

    @Column(name = "MEM_DET_EDU")
    private String memberDetailEdu;

    @Column(name = "MEM_DET_MJR")
    private String memberDetailMajor;

    @Column(name = "MEM_DET_EMP_DATE")
    private String memberDetailEmpDate;

    @Column(name = "MEM_DET_RTR_DATE")
    private String memberDetailRtrDate;

    @Column(name = "CAR_INFO")
    private String memberDetailCareerInfo;

    @Column(name = "CERT_DATE")
    private String memberDetailCertificationDate;

    @Column(name = "CERT_INST")
    private String memberDetailCertificationInst;

    @Column(name = "CERT_NAME")
    private String memberDetailCertificationName;

    @Column(name = "CERT_SCO")
    private String memberDetailCertificationScore;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

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
