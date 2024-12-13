package stanl_2.final_backend.domain.product.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSelectAllDTO {
    private String productId;
    private String name;
    private String serialNumber;
    private String cost;
    private String stock;
}
