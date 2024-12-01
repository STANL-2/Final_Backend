package stanl_2.final_backend.domain.customer.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerSearchDTO {
    private String customerId;
    private String name;
    private String sex;
    private String phone;
    private String memberId;

    public CustomerSearchDTO(String customerId, String name, String sex, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }
}
