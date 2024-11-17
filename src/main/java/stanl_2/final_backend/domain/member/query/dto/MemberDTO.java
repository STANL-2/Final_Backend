package stanl_2.final_backend.domain.member.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberDTO {
    private String loginId;
    private String name;
    private String email;
    private Integer age;
    private String sex;
    private String identNo;
    private String phone;
    private String emergePhone;
    private String address;
    private String note;
    private String position;
    private String grade;
    private String jobType;
    private String military;
    private String bankName;
    private String account;
}