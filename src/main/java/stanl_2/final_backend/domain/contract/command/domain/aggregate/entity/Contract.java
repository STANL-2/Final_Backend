package stanl_2.final_backend.domain.contract.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "CONR_TTL", nullable = false)
    private String title;

    @Column(name = "CONR_CUST_NAME", nullable = false)
    private String customerName;

    @Column(name = "CONR_CUST_SEX", nullable = false)
    private String customerSex;

    @Column(name = "CONR_CUST_IDEN_NO", nullable = false)
    private String customerIdentifiNo;

    @Column(name = "CONR_CUST_AGE", nullable = false)
    private String customerAge;

    @Column(name = "CONR_CUST_ADR", nullable = false)
    private String customerAddress;

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
    private String deliveryDate;

    @Column(name = "CONR_DELV_LOC")
    private String deliveryLocation;

    @Column(name = "CONR_CAR_NAME")
    private String carName;

    @Column(name = "CONR_STAT", nullable = false)
    private String status = "WAIT";

    @Column(name = "CONR_NO_OF_VEH", nullable = false)
    private Integer numberOfVehicles = 1;

    @Column(name = "CONR_TOTA_SALE", nullable = false)
    private Integer totalSales = 0;

    @Column(name = "CONR_VEHI_PRIC", nullable = false)
    private Integer vehiclePrice = 0;

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

    @Column(name = "ADMI_ID")
    private String adminId;

    @Column(name = "CENT_ID", nullable = false)
    private String centerId;

    @Column(name = "CUST_ID", nullable = false)
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
