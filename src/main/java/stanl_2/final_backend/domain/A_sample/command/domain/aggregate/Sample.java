package stanl_2.final_backend.domain.A_sample.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SAMPLE")
public class Sample {

    @Id
    @Column(name = "SAM_ID")
    private String id;

    @Column(name = "SAM_NAME")
    private String name;

    @Column(name = "SAM_NUM")
    private Integer num;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "DELETE_AT")
    private Timestamp deletedAt;


    // 영속성 생성시 시간 설정
    @PrePersist
    private void prePersist() {
        this.createdAt = getCurrentTimestamp();
        this.updatedAt = getCurrentTimestamp();
    }

    // 영속성 수정시 시간 설정
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = getCurrentTimestamp();
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

}
