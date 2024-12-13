package stanl_2.final_backend.domain.product.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.product.query.dto.ProductSearchRequestDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectAllDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;

import java.util.Map;

@Service
public interface ProductQueryService {
    Page<ProductSelectAllDTO> selectAll(Pageable pageable);

    ProductSelectIdDTO selectByProductId(String productId);

    Page<ProductSelectAllDTO> selectProductBySearch(ProductSearchRequestDTO productSearchRequestDTO, Pageable pageable);

    ProductSelectIdDTO selectByProductSerialNumber(String id);

    void exportProductsToExcel(HttpServletResponse response);
}
