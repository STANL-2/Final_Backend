package stanl_2.final_backend.domain.sales_history.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryRankedDataDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryStatisticsDTO;

public interface SalesHistoryQueryService {
    Page<SalesHistorySelectDTO> selectAllSalesHistoryByEmployee(SalesHistorySelectDTO salesHistorySelectDTO, Pageable pageable);

    SalesHistorySelectDTO selectSalesHistoryDetail(SalesHistorySelectDTO salesHistorySelectDTO);

    Page<SalesHistorySelectDTO> selectAllSalesHistory(Pageable pageable);

    SalesHistoryStatisticsDTO selectStatisticsByEmployee(SalesHistorySelectDTO salesHistorySelectDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchMonthByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchYearByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    Page<SalesHistoryRankedDataDTO> selectStatistics(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsCenterBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsBySearchMonth(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsBySearchYear(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsCenterBySearchMonth(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsCenterBySearchYear(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);
}
