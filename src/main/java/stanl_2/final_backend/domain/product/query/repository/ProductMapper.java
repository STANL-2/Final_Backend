package stanl_2.final_backend.domain.product.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleExcelDownload;
import stanl_2.final_backend.domain.product.common.util.RequestList;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    List<Map<String, Object>> findProductAll(RequestList<?> requestList);

    int findProductCount();

    ProductSelectIdDTO findProductById(String id);

    List<Map<String, Object>> findProductBySearch(Map<String, Object> paramMap);

    int findProductBySearchCount(Map<String, Object> paramMap);

    ProductSelectIdDTO findProductBySerialNumber(String serialNumber);

    List<SampleExcelDownload> findProductsForExcel();
}
