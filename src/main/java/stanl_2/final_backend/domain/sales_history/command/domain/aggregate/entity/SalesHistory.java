package stanl_2.final_backend.domain.sales_history.command.domain.aggregate.entity;

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
@Table(name = "TB_SALES_HISTORY")
public class SalesHistory {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "SAL")
    )
    @Column(name = "SAL_HIST_ID")
    private String salesHistoryId;

    @Column(name = "SAL_HIST_NO_OF_VEH", nullable = false)
    private Integer numberOfVehicles;

    @Column(name = "SAL_HIST_TOTA_SALE", nullable = false)
    private Integer totalSales;

    @Column(name = "SAL_HIST_INCE", nullable = false)
    private Double incentive;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "CONR_ID", nullable = false)
    private String contractId;

    @Column(name = "CUST_ID", nullable = false)
    private String customerInfoId;

    @Column(name = "PROD_ID", nullable = false)
    private String productId;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    @Column(name = "CENT_ID", nullable = false)
    private String centerId;

    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTime();
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
