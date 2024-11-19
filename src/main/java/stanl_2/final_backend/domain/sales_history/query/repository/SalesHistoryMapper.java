package stanl_2.final_backend.domain.sales_history.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;

import java.util.List;

@Mapper
public interface SalesHistoryMapper {
    List<SalesHistorySelectDTO> findAllSalesHistory(@Param("size") int size
                                                    ,@Param("offset") int offset);

    int findSalesHistoryCount();

    List<SalesHistorySelectDTO> findSalesHistoryByMember(@Param("size") int size
            ,@Param("offset") int offset
            ,@Param("searcherId") String searcherId);

    int findSalesHistoryCountByMember(@Param("searcherId") String searcherId);
}
