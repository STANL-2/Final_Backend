package stanl_2.final_backend.domain.member.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SignupDTO {
//    private String id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private Integer age;
    private String sex;
    private String idenNo;
    private String phone;
    private String emergPhone;
    private String address;
    private String note;
    private String position;
    private String grade;
    private String jobType;
    private String military;
    private String bankName;
    private String account;
}
