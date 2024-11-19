package stanl_2.final_backend.domain.customer.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerRegistDTO {
    private String name;
    private Integer age;
    private String sex;
    private String phone;
    private String emergePhone;
    private String email;
    private String memberId;
}
