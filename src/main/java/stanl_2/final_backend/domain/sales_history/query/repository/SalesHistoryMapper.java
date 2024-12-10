package stanl_2.final_backend.domain.sales_history.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.sales_history.query.dto.*;

import java.util.List;

@Mapper
public interface SalesHistoryMapper {

    List<SalesHistorySelectDTO> findSalesHistoryByEmployee(@Param("size") int size
            , @Param("offset") int offset
            , @Param("searcherId") String searcherId,
                                                           @Param("sortField") String sortField,
                                                           @Param("sortOrder") String sortOrder);

    int findSalesHistoryCountByEmployee(@Param("searcherId") String searcherId);

    List<SalesHistorySelectDTO> findAllSalesHistory(@Param("size") int size
            , @Param("offset") int offset,
                                                    @Param("sortField") String sortField,
                                                    @Param("sortOrder") String sortOrder);

    SalesHistorySelectDTO findSalesHistoryDetail(@Param("salesHistorySelectDTO") SalesHistorySelectDTO salesHistorySelectDTO);

    int findSalesHistoryCount();

    SalesHistoryStatisticsDTO findStatisticsByEmployee(@Param("salesHistorySelectDTO") SalesHistorySelectDTO salesHistorySelectDTO);

    SalesHistoryStatisticsDTO findStatisticsSearchByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsDTO> findStatisticsSearchByEmployeeDaily(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsDTO> findStatisticsSearchMonthByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsDTO> findStatisticsSearchYearByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryRankedDataDTO> findAllRank(@Param("salesHistoryRankedDataDTO")SalesHistoryRankedDataDTO salesHistoryRankedDataDTO
            , @Param("size") int size
            , @Param("offset") int offset);

    int findRankCount();

    List<SalesHistoryRankedDataDTO> findStatisticsBySearch(@Param("size") int size
                                                        , @Param("offset") int offset,
                                                           @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsBySearchCount(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    int findStatisticsBySearchCountMonth(@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistorySelectDTO> findSalesHistorySearchByEmployee(@Param("size") int size
                                                                , @Param("offset") int offset,
                                                                 @Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO,
                                                                 @Param("sortField") String sortField,
                                                                 @Param("sortOrder") String sortOrder);

    int findSalesHistorySearchCountByEmployee(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistorySelectDTO> findSalesHistoryBySearch(@Param("size") int size
                                                        , @Param("offset") int offset,
                                                         @Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO,
                                                         @Param("sortField") String sortField,
                                                         @Param("sortOrder") String sortOrder);

    int findSalesHistoryCountBySearch(@Param("salesHistorySearchDTO") SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsAverageDTO> findStatisticsAverageBySearch(@Param("size") int size
            , @Param("offset") int offset
            ,@Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryExcelDownload> findSalesHistoryForExcel();

    String findSalesHistoryIdByContractId(@Param("contractId") String contractId);

    List<SalesHistoryRankedDataDTO> findStatisticsBestBySearch(@Param("size") int size
            , @Param("offset") int offset,
                                                               @Param("salesHistoryRankedDataDTO") SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);

    List<SalesHistoryRankedDataDTO> findAllStatisticsBySearch(int size, int offset, SalesHistoryRankedDataDTO salesHistoryRankedDataDTO);
}


