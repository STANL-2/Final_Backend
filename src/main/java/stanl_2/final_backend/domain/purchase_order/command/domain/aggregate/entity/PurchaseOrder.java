package stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity;

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
@Table(name = "TB_PURCHASE_ORDER")
public class PurchaseOrder {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "PUR_ORD")
    )
    @Column(name = "PUR_ORD_ID")
    private String purchaseOrderId;

    @Column(name = "PUR_ORD_TTL", nullable = false)
    private String title;

    @Lob
    @Column(name = "PUR_ORD_CONT", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "STAT_UPDATED_AT")
    private String statusUpdatedAt;

    @Column(name = "PUR_ORD_STAT", nullable = false)
    private String status = "WAIT";

    @Column(name = "ORD_ID", nullable = false)
    private String orderId;

    @Column(name = "ADMIN_ID")
    private String adminId;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

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
