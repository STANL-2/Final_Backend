package stanl_2.final_backend.domain.product.command.application.command.service;

public interface ProductCommandService {
    void modifyProductStock(String productId, Integer numberOfVehicles);

    void deleteProductStock(String productId, Integer numberOfVehicles);
}
