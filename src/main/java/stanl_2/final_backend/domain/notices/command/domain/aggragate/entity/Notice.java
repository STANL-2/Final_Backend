package stanl_2.final_backend.domain.notices.command.domain.aggragate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

import java.sql.Timestamp;

@Entity
@Table(name="NOTICE")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Notice {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name="prefix", value = "NOT")
    )
    @Column(name = "NOT_ID")
    private String id;

    @Column(name = "NOT_TTL")
    private String title;

    @Column(name = "NOT_TAG")
    private String tag;

    @Column(name = "NOT_CLA")
    private String classification;

    @Column(columnDefinition = "TEXT", name = "content")
    private String content;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Timestamp createdAt;
}
