package stanl_2.final_backend.domain.center.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import stanl_2.final_backend.domain.center.common.util.RequestList;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CenterMapper {
    CenterSelectIdDTO findCenterById(String id);

    List<CenterSelectAllDTO> findCenterAll(@Param("size") int size
            , @Param("offset") int offset);

    Integer findCenterCount();

    Integer findCenterBySearchCount(@Param("centerSearchRequestDTO") CenterSearchRequestDTO centerSearchRequestDTO);

    List<CenterSelectAllDTO> findCenterBySearch(@Param("size") int size
                                                , @Param("offset") int offset
                                                , @Param("centerSearchRequestDTO") CenterSearchRequestDTO centerSearchRequestDTO);

}
