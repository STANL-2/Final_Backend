package stanl_2.final_backend.domain.member.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.final_backend.domain.member.aggregate.Role;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "MEMBER_LOGIN_ID")
    @NotNull
    private String loginId;

    @Column(name = "MEMBER_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "MEMBER_EMAIL")
    @NotNull
    private String email;

    @Column(name = "MEMBER_NAME")
    @NotNull
    private String name;

    @Column(name = "MEMBER_PHONE")
    @NotNull
    private String phone;

    @Column(name = "MEMBER_ROLE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column(name = "MEMBER_CREATED_AT")
    @NotNull
    private Timestamp createdAt;

    @Column(name = "MEMBER_UPDATED_AT")
    @NotNull
    private Timestamp updatedAt;

    @Column(name = "MEMBER_ACTIVE")
    @NotNull
    private Boolean active = true;
}
