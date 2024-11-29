package stanl_2.final_backend.domain.product.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleExcelDownload;
import stanl_2.final_backend.domain.product.common.exception.ProductCommonException;
import stanl_2.final_backend.domain.product.common.exception.ProductErrorCode;
import stanl_2.final_backend.domain.product.common.util.RequestList;
import stanl_2.final_backend.domain.product.query.dto.ProductExcelDownload;
import stanl_2.final_backend.domain.product.query.dto.ProductSearchRequestDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectAllDTO;
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
    public Page<ProductSelectAllDTO> selectAll(Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<ProductSelectAllDTO> productList = productMapper.findProductAll(size, offset, sortField, sortOrder);

        int total = productMapper.findProductCount();

        if(productList == null || total == 0) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return new PageImpl<>(productList, pageable, total);

    }

    @Override
    @Transactional
    public ProductSelectIdDTO selectByProductId(String productId) {
        ProductSelectIdDTO productSelectIdDTO = productMapper.findProductById(productId);

        if(productSelectIdDTO == null) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return productSelectIdDTO;
    }

    @Override
    @Transactional
    public Page<ProductSelectAllDTO> selectProductBySearch(ProductSearchRequestDTO productSearchRequestDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<ProductSelectAllDTO> productList = productMapper.findProductBySearch(size, offset, productSearchRequestDTO, sortField, sortOrder);

        int total = productMapper.findProductBySearchCount(productSearchRequestDTO);

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
        List<ProductExcelDownload> productList = productMapper.findProductsForExcel();

        if(productList == null) {
            throw new ProductCommonException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        excelUtilsV1.download(ProductExcelDownload.class, productList, "productExcel", response);
    }
}
