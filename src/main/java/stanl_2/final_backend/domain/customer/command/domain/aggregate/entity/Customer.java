package stanl_2.final_backend.domain.customer.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "TB_CUSTOMER_INFO")
public class Customer {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CUS")
    )
    @Column(name = "CUST_ID", nullable = false)
    private String customerId;

    @Column(name = "CUST_NAME", nullable = false)
    private String name;

    @Column(name = "CUST_AGE", nullable = false)
    private Integer age;

    @Column(name = "CUST_SEX", nullable = false)
    private String sex;

    @Column(name = "CUST_PHO", nullable = false)
    private String phone;

    @Column(name = "CUST_EMER_PHO")
    private String emergePhone;

    @Column(name = "CUST_EMA", nullable = false)
    private String email;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String  createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String  updatedAt;

    @Column(name = "DELETED_AT")
    private String  deletedAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

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
