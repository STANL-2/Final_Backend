package stanl_2.final_backend.domain.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_MEMBER_ROLE")
public class MemberRole {

    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "MEM_ROL")
    )
    @Column(name = "MEM_ROL_ID", nullable = false)
    private String memberRoleId;

    @Column(name = "MEM_ROL_NAME", nullable = false)
    private String role;

    @Column(name = "MEM_ID", nullable = false, updatable = false)
    private String memberId;

}
