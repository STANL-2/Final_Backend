package stanl_2.final_backend.domain.product.command.application.domain.service;

import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.product.command.application.command.service.ProductCommandService;
import stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity.Product;
import stanl_2.final_backend.domain.product.command.application.domain.repository.ProductRepository;
import stanl_2.final_backend.domain.product.common.exception.ProductCommonException;
import stanl_2.final_backend.domain.product.common.exception.ProductErrorCode;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void modifyProductStock(String productId, int numberOfVehicles) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.setStock(product.getStock() - numberOfVehicles);

        productRepository.save(product);
    }

    @Override
    public void deleteProductStock(String productId, int numberOfVehicles) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.setStock(product.getStock() + numberOfVehicles);
        productRepository.save(product);
    }
}
