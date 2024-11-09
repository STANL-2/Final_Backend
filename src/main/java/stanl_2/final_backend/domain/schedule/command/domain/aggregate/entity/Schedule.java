package stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    @Id
    @Column(name = "SCH_ID", nullable = false)
    private String id;

    @Column(name = "SCH_NAME", nullable = false)
    private String name;

    @Column(name = "SCH_CONT", nullable = false)
    private String content;

    @Column(name = "SCH_RES", nullable = false)
    private String reservationTime;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;
}
