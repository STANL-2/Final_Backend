package stanl_2.final_backend.domain.product.command.application.command.service;

public interface ProductCommandService {
    void modifyProductStock(String productId, int numberOfVehicles);

    void deleteProductStock(String productId, int numberOfVehicles);
}
