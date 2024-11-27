package stanl_2.final_backend.domain.product.command.application.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.product.command.application.command.dto.ProductRegistDTO;
import stanl_2.final_backend.domain.product.command.application.command.service.ProductCommandService;
import stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity.Product;
import stanl_2.final_backend.domain.product.command.application.domain.repository.ProductRepository;
import stanl_2.final_backend.domain.product.common.exception.ProductCommonException;
import stanl_2.final_backend.domain.product.common.exception.ProductErrorCode;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final S3FileService s3FileService;

    public ProductCommandServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, S3FileService s3FileService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.s3FileService = s3FileService;
    }

    @Override
    @Transactional
    public void modifyProductStock(String productId, Integer numberOfVehicles) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.setStock(product.getStock() - numberOfVehicles);

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductStock(String productId, Integer numberOfVehicles) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.setStock(product.getStock() + numberOfVehicles);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void registProduct(ProductRegistDTO productRegistDTO, MultipartFile imageUrl) {
        Product newProduct = modelMapper.map(productRegistDTO, Product.class);

        newProduct.setImageUrl(s3FileService.uploadOneFile(imageUrl));

        productRepository.save(newProduct);
    }
}
