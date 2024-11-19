package stanl_2.final_backend.domain.product.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;

import java.util.Map;

@Service
public interface ProductService {
    Page<Map<String, Object>> selectAll(Pageable pageable);

    ProductSelectIdDTO selectByProductId(String id);

    Page<Map<String, Object>> selectProductBySearch(Map<String, Object> paramMap);

    @Transactional
    ProductSelectIdDTO selectByProductSerialNumber(String id);
}
