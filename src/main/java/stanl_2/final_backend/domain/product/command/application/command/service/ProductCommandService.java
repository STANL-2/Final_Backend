package stanl_2.final_backend.domain.product.command.application.command.service;

public interface ProductCommandService {
    void modifyProductStock(String productId);

    void deleteProductStock(String productId);
}
