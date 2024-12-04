package stanl_2.final_backend.domain.member.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@Setter
@AllArgsConstructor
public class MemberExcelDTO {

    @ExcelColumnName(name = "사원 번호")
    private String loginId;

    @ExcelColumnName(name = "사원명")
    private String name;

    @ExcelColumnName(name = "사원 이메일")
    private String email;

    @ExcelColumnName(name = "사원 나이")
    private Integer age;

    @ExcelColumnName(name = "사원 성별")
    private String sex;

    @ExcelColumnName(name = "사원 연락처")
    private String phone;

    @ExcelColumnName(name = "사원 비상연락처")
    private String emergePhone;

    @ExcelColumnName(name = "사원 주소")
    private String address;

    @ExcelColumnName(name = "비고")
    private String note;

    @ExcelColumnName(name = "직급")
    private String position;

    @ExcelColumnName(name = "학력")
    private String grade;

    @ExcelColumnName(name = "고용형태")
    private String jobType;

    @ExcelColumnName(name = "병역구분")
    private String military;

    @ExcelColumnName(name = "은행명")
    private String bankName;

    @ExcelColumnName(name = "계좌 번호")
    private String account;
}
