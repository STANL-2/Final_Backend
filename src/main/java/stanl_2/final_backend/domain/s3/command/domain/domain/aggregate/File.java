package stanl_2.final_backend.domain.s3.command.domain.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

@Entity
@Table(name="TB_FILE")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class File {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name="prefix", value = "FIL")
    )
    @Column(name = "FIL_ID")
    private String fileId;

    @Column(name = "FIL_URL")
    private String fileUrl;
}
