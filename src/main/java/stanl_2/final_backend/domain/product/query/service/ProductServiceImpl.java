package stanl_2.final_backend.domain.product.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.product.common.util.RequestList;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.repository.ProductMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public Page<Map<String, Object>> selectAll(Pageable pageable) {
        RequestList<?> requestList = RequestList.builder()
                .pageable(pageable)
                .build();

        List<Map<String, Object>> productList = productMapper.findProductAll(requestList);

        int total = productMapper.findProductCount();

        return new PageImpl<>(productList, pageable, total);

    }

    @Override
    @Transactional
    public ProductSelectIdDTO selectByProductId(String id) {

        /* 설명. 상세 조회 시, Mapper에서 product와 productOption join해서 보여줄 것 */
        ProductSelectIdDTO productSelectIdDTO = productMapper.findProductById(id);

        return productSelectIdDTO;
    }

    @Override
    @Transactional
    public Page<Map<String, Object>> selectProductBySearch(Map<String, Object> paramMap) {

        Pageable pageable = (Pageable) paramMap.get("pageable");

        List<Map<String, Object>> productList = productMapper.findProductBySearch(paramMap);

        int total = productMapper.findProductBySearchCount(paramMap);

        return new PageImpl<>(productList, pageable, total);
    }
}
