package stanl_2.final_backend.domain.center.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/* 설명. 테스트를 위한 어노테이션(나중에 삭제 예정)*/
@ToString

@Entity
@Table(name="CENTER")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Center {

    /* 설명. 테스트를 위한 Long 선언 => mem_0000001 구현을 위해서는 고도화 진행 필요*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CENT_ID", nullable = false)
    private Long id;

    @Column(name = "CENT_NAME", nullable = false)
    private String name;

    @Column(name = "CENT_ADR", nullable = false)
    private String address;

    @Column(name = "CENT_PHO", nullable = false)
    private String phone;

    @Column(name = "CENT_MEM_CNT", nullable = false)
    private Integer memberCount;

    @Column(name = "CENT_OPR_AT", nullable = false)
    private String operatingAt;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    // Insert 되기 전에 실행
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // Update 되기 전에 실행
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
