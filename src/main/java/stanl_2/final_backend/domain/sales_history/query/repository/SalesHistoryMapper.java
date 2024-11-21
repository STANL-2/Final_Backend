package stanl_2.final_backend.domain.sales_history.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryStatisticsDTO;

import java.util.List;

@Mapper
public interface SalesHistoryMapper {

    List<SalesHistorySelectDTO> findSalesHistoryByEmployee(@Param("size") int size
            ,@Param("offset") int offset
            ,@Param("searcherId") String searcherId);

    int findSalesHistoryCountByEmployee(@Param("searcherId") String searcherId);

    List<SalesHistorySelectDTO> findAllSalesHistory(@Param("size") int size
                                                    ,@Param("offset") int offset);

    SalesHistorySelectDTO findSalesHistoryDetail(@Param("salesHistorySelectDTO") SalesHistorySelectDTO salesHistorySelectDTO);

    int findSalesHistoryCount();

    SalesHistoryStatisticsDTO findStatisticsByEmployee(@Param("salesHistorySelectDTO") SalesHistorySelectDTO salesHistorySelectDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchMonthByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchYearByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);
}
