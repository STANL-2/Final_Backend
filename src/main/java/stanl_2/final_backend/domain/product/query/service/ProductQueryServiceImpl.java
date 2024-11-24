package stanl_2.final_backend.domain.product.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleExcelDownload;
import stanl_2.final_backend.domain.product.common.exception.ProductCommonException;
import stanl_2.final_backend.domain.product.common.exception.ProductErrorCode;
import stanl_2.final_backend.domain.product.common.util.RequestList;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.repository.ProductMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.util.List;
import java.util.Map;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductMapper productMapper;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public ProductQueryServiceImpl(ProductMapper productMapper, ExcelUtilsV1 excelUtilsV1) {
        this.productMapper = productMapper;
        this.excelUtilsV1 = excelUtilsV1;
    }

    @Override
    @Transactional
    public Page<Map<String, Object>> selectAll(Pageable pageable) {
        RequestList<?> requestList = RequestList.builder()
                .pageable(pageable)
                .build();

        List<Map<String, Object>> productList = productMapper.findProductAll(requestList);

        int total = productMapper.findProductCount();

        if(productList == null || total == 0) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return new PageImpl<>(productList, pageable, total);

    }

    @Override
    @Transactional
    public ProductSelectIdDTO selectByProductId(String id) {
        ProductSelectIdDTO productSelectIdDTO = productMapper.findProductById(id);

        if(productSelectIdDTO == null) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return productSelectIdDTO;
    }

    @Override
    @Transactional
    public Page<Map<String, Object>> selectProductBySearch(Map<String, Object> paramMap) {

        Pageable pageable = (Pageable) paramMap.get("pageable");

        List<Map<String, Object>> productList = productMapper.findProductBySearch(paramMap);

        int total = productMapper.findProductBySearchCount(paramMap);

        if(productList == null || total == 0) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return new PageImpl<>(productList, pageable, total);
    }

    @Override
    @Transactional
    public ProductSelectIdDTO selectByProductSerialNumber(String id) {

        ProductSelectIdDTO productSelectIdDTO = productMapper.findProductBySerialNumber(id);

        if(productSelectIdDTO == null) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return productSelectIdDTO;
    }

    @Override
    @Transactional
    public void exportProductsToExcel(HttpServletResponse response) {
        List<SampleExcelDownload> productList = productMapper.findProductsForExcel();

        if(productList == null) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        excelUtilsV1.download(SampleExcelDownload.class, productList, "productExcel", response);
    }
}
