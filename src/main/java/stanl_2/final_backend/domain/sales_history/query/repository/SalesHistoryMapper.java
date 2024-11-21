package stanl_2.final_backend.domain.sales_history.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryRankedDataDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryStatisticsDTO;

import java.util.List;

@Mapper
public interface SalesHistoryMapper {

    List<SalesHistorySelectDTO> findSalesHistoryByEmployee(@Param("size") int size
            , @Param("offset") int offset
            , @Param("searcherId") String searcherId);

    int findSalesHistoryCountByEmployee(@Param("searcherId") String searcherId);

    List<SalesHistorySelectDTO> findAllSalesHistory(@Param("size") int size
            , @Param("offset") int offset);

    SalesHistorySelectDTO findSalesHistoryDetail(@Param("salesHistorySelectDTO") SalesHistorySelectDTO salesHistorySelectDTO);

    int findSalesHistoryCount();

    SalesHistoryStatisticsDTO findStatisticsByEmployee(@Param("salesHistorySelectDTO") SalesHistorySelectDTO salesHistorySelectDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchMonthByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchYearByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryRankedDataDTO> findAllRank(@Param("salesHistoryRankedDataDTO")SalesHistoryRankedDataDTO salesHistoryRankedDataDTO
            , @Param("size") int size
            , @Param("offset") int offset);

    int findRankCount();

    List<SalesHistoryRankedDataDTO> findStatisticsBySearch(@Param("size") int size
                                                        , @Param("offset") int offset,
                                                           @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsBySearchCount(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryRankedDataDTO> findStatisticsCenterBySearch(@Param("size") int size
                                                           , @Param("offset") int offset,
                                                             @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsCenterBySearchCount(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryRankedDataDTO> findStatisticsBySearchMonth(@Param("size") int size
            , @Param("offset") int offset,
                                                                @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsBySearchCountMonth(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryRankedDataDTO> findStatisticsBySearchYear(@Param("size") int size
            , @Param("offset") int offset,
                                                               @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsBySearchCountYear(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryRankedDataDTO> findStatisticsCenterBySearchMonth(@Param("size") int size
            , @Param("offset") int offset,
                                                                      @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsCenterBySearchCountMonth(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryRankedDataDTO> findStatisticsCenterBySearchYear(@Param("size") int size
            , @Param("offset") int offset,
                                                                     @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsCenterBySearchCountYear(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);
}


