package stanl_2.final_backend.domain.product.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductSearchRequestDTO {
    private String productId;
    private String name;
    private String serialNumber;
}
