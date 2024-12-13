package stanl_2.final_backend.domain.problem.command.domain.aggregate.entity;

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

@Entity
@Table(name="TB_PROBLEM")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Problem {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name="prefix", value = "PROB")
    )
    @Column(name = "PROB_ID")
    private String problemId;

    @Column(name = "PROB_TTL", nullable = false)
    private String title;

    @Column(name = "PROB_CONT",columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, length=19)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false, length=19)
    private String updatedAt;

    @Column(name = "DELETED_AT", length=19)
    private String deletedAt;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "CST_ID")
    private String customerId;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    @Column(name = "PROD_ID", nullable = false)
    private String productId;

    @Column(name ="PROB_STATUS",nullable = false)
    private String status = "PROGRESS";

    @Column(name = "FILE_URL")
    private String fileUrl;

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
