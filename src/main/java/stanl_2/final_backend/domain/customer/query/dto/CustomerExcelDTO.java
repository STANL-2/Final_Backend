package stanl_2.final_backend.domain.customer.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class CustomerExcelDTO {

    @ExcelColumnName(name = "고객 번호")
    private String customerId;

    @ExcelColumnName(name = "고객명")
    private String name;

    @ExcelColumnName(name = "고객 나이")
    private Integer age;

    @ExcelColumnName(name = "고객 성별")
    private String sex;

    @ExcelColumnName(name = "고객 연락처")
    private String phone;

    @ExcelColumnName(name = "고객 비상연락처")
    private String emergePhone;

    @ExcelColumnName(name = "고객 메일")
    private String email;

    @ExcelColumnName(name = "고객 담당자(사원) 번호")
    private String memberId;
}
