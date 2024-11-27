package stanl_2.final_backend.domain.customer.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerModifyDTO {
    private String memberId;
    private String customerId;
    private String name;
    private Integer age;
    private String sex;
    private String phone;
    private String emergePhone;
    private String email;
}
