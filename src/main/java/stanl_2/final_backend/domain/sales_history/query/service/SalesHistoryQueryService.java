package stanl_2.final_backend.domain.sales_history.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.sales_history.query.dto.*;

import java.security.GeneralSecurityException;
import java.util.List;

public interface SalesHistoryQueryService {
    Page<SalesHistorySelectDTO> selectAllSalesHistoryByEmployee(SalesHistorySelectDTO salesHistorySelectDTO, Pageable pageable);

    SalesHistorySelectDTO selectSalesHistoryDetail(SalesHistorySelectDTO salesHistorySelectDTO);

    Page<SalesHistorySelectDTO> selectAllSalesHistory(Pageable pageable);

    SalesHistoryStatisticsDTO selectStatisticsByEmployee(SalesHistorySelectDTO salesHistorySelectDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsDTO> selectStatisticsSearchByEmployeeDaily(SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsDTO> selectStatisticsSearchMonthByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    List<SalesHistoryStatisticsDTO> selectStatisticsSearchYearByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    Page<SalesHistoryRankedDataDTO> selectStatistics(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectStatisticsBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistorySelectDTO> selectSalesHistorySearchByEmployee(SalesHistorySearchDTO salesHistorySearchDTO, Pageable pageable);

    Page<SalesHistorySelectDTO> selectSalesHistoryBySearch(SalesHistorySearchDTO salesHistorySearchDTO, Pageable pageable) throws GeneralSecurityException;

    Page<SalesHistoryStatisticsAverageDTO> selectStatisticsAverageBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    void exportSalesHistoryToExcel(HttpServletResponse response);

    String selectSalesHistoryIdByContractId(String contractId);

    Page<SalesHistoryRankedDataDTO> selectStatisticsBestBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    @Transactional
    Page<SalesHistoryRankedDataDTO> selectAllStatstics(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);

    Page<SalesHistoryRankedDataDTO> selectMyStatisticsBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable);
}
