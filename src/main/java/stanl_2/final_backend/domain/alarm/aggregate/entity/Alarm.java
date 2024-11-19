package stanl_2.final_backend.domain.alarm.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

@Entity
@Table(name = "TB_ALARM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @Parameter(name = "prefix", value = "ALR")
    )
    @Column(name = "ALR_ID", nullable = false)
    private String alarmId;

    @Column(name = "ALR_MSG", nullable = false)
    private String message;

    @Column(name = "ALR_URL", nullable = false)
    private String redirectUrl;

    @Column(name = "ALR_TYPE", nullable = false)
    private String type = "NOTICE";

    @Column(name = "ALR_READ_STAT", nullable = false)
    private Boolean readStatus;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

//    @Column(name = "ALR_STAT", nullable = false)
//    private String status = "PENDING";

    @Column(name = "MEM_ID", nullable = false)
    private String memberId;


}
