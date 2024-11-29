package stanl_2.final_backend.domain.product.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleExcelDownload;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.product.common.util.RequestList;
import stanl_2.final_backend.domain.product.query.dto.ProductExcelDownload;
import stanl_2.final_backend.domain.product.query.dto.ProductSearchRequestDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectAllDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    List<ProductSelectAllDTO> findProductAll(@Param("size") int size
                                       , @Param("offset") int offset,
                                             @Param("sortField") String sortField,
                                             @Param("sortOrder") String sortOrder);

    int findProductCount();

    ProductSelectIdDTO findProductById(String productId);

    List<ProductSelectAllDTO> findProductBySearch(@Param("size") int size
            , @Param("offset") int offset
            , @Param("productSearchRequestDTO") ProductSearchRequestDTO productSearchRequestDTO,
                                                  @Param("sortField") String sortField,
                                                  @Param("sortOrder") String sortOrder);

    int findProductBySearchCount(@Param("productSearchRequestDTO") ProductSearchRequestDTO productSearchRequestDTO);

    ProductSelectIdDTO findProductBySerialNumber(String serialNumber);

    List<ProductExcelDownload> findProductsForExcel();
}
