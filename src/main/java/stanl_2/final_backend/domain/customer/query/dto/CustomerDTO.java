package stanl_2.final_backend.domain.customer.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private String customerId;
    private String name;
    private Integer age;
    private String sex;
    private String phone;
    private String emergePhone;
    private String email;
    private String memberId;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
