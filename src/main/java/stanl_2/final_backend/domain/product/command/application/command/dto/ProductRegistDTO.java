package stanl_2.final_backend.domain.product.command.application.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductRegistDTO {
    private String name;
    private String serialNumber;
    private String cost;
    private String stock;
    private String imageUrl;
}
