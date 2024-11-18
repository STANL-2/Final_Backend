package stanl_2.final_backend.domain.education.command.domain.aggregate.entity;

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
@Table(name = "tb_education")
public class Education {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "EDU")
    )
    @Column(name = "EDU_ID", nullable = false)
    private String educationId;

    @Column(name = "EDU_ENTD", nullable = false)
    private String entranceDate;

    @Column(name = "EDU_GRAD", nullable = false)
    private String graduationDate;

    @Column(name = "EDU_NAME", nullable = false)
    private String name;

    @Column(name = "EDU_MJR", nullable = false)
    private String major;

    @Column(name = "EDU_SCO")
    private String score;

    @Column(name = "EDU_NOTE")
    private String note;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private String  createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String  updatedAt;

    @Column(name = "MEM_ID", nullable = false)
    private String memId;

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
