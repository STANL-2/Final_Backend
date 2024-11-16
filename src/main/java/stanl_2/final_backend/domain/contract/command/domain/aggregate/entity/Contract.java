package stanl_2.final_backend.domain.contract.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "TB_CONTRACT")
public class Contract {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CON")
    )
    @Column(name = "CONR_ID")
    private String contractId;

    @Column(name = "CONR_NAME", nullable = false)
    private String name;

    @Column(name = "CONR_CUST_NAME", nullable = false)
    private String customerName;

    @Column(name = "CONR_CUST_IDEN_NO", nullable = false)
    private String customerIdentifiNo;

    @Column(name = "CONR_CUST_ADR", nullable = false)
    private String customerAddrress;

    @Column(name = "CONR_CUST_EMA", nullable = false)
    private String customerEmail;

    @Column(name = "CONR_CUST_PHO", nullable = false)
    private String customerPhone;

    @Column(name = "CONR_COMP_NAME")
    private String companyName;

    @Column(name = "CONR_CUST_CLA", nullable = false)
    @ColumnDefault("'PERSONAL'")
    private String customerClassifcation;

    @Column(name = "CONR_CUST_PUR_COND", nullable = false)
    @ColumnDefault("'CASH'")
    private String customerPurchaseCondition;

    @Column(name = "CONR_SERI_NUM", nullable = false)
    private String serialNum;

    @Column(name = "CONR_SELE_OPTI", nullable = false)
    private String selectOption;

    @Column(name = "CONR_DOWN_PAY", nullable = false)
    private Integer downPayment;

    @Column(name = "CONR_INTE_PAY", nullable = false)
    private Integer intermediatePayment;

    @Column(name = "CONR_REM_PAY", nullable = false)
    private Integer remainderPayment;

    @Column(name = "CONR_CONS_PAY", nullable = false)
    private Integer consignmentPayment;

    @Column(name = "CONR_DELV_DATE")
    private String delveryDate;

    @Column(name = "CONR_DELV_LOC")
    private String delveryLocation;

    @Column(name = "CONR_STAT", nullable = false)
    @ColumnDefault("'WAIT'")
    private String status;

    @Column(name = "CONR_NO_OF_VEH", nullable = false)
    @ColumnDefault("1")
    private String NumberOfVehicles;

    @Lob
    @Column(name = "CREATED_URL", nullable = false, columnDefinition = "TEXT")
    private String createdUrl;

    @Lob
    @Column(name = "DELETED_URL", columnDefinition = "TEXT")
    private String deletedUrl;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    @Column(name = "CENT_ID", nullable = false)
    private String centerId;

    @Column(name = "CUST_ID")
    private String customerId;

    @Column(name = "PROD_ID", nullable = false)
    private String productId;

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

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
