package stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="TB_SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Schedule {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
                      type = PrefixGeneratorConfig.class,
                      parameters = @Parameter(name = "prefix", value = "SCH")
    )
    @Column(name = "SCH_ID", nullable = false)
    private String scheduleId;

    @Column(name = "SCH_NAME", nullable = false)
    private String name;

    @Column(name = "SCH_CONT", nullable = false)
    private String content;

    @Column(name = "SCH_TAG", nullable = false)
    private String tag = "CONSULTATION";

    @Column(name = "SCH_SRT_AT", nullable = false)
    private String startAt;

    @Column(name = "SCH_END_AT", nullable = false)
    private String endAt;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;

    /* 설명. updatedAt 자동화 */
    // Insert 되기 전에 실행
    @PrePersist
    public void prePersist() {
        this.createdAt = getCurrentTime();
        this.updatedAt = this.createdAt;
    }

    // Update 되기 전에 실행
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = getCurrentTime();
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
