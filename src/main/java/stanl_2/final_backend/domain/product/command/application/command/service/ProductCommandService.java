package stanl_2.final_backend.domain.product.command.application.command.service;

import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.product.command.application.command.dto.ProductRegistDTO;

public interface ProductCommandService {
    void modifyProductStock(String productId, Integer numberOfVehicles);

    void deleteProductStock(String productId, Integer numberOfVehicles);

    void registProduct(ProductRegistDTO productRegistDTO, MultipartFile imageUrl);
}
