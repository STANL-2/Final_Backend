package stanl_2.final_backend.domain.sales_history.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryStatisticsDTO;

import java.security.GeneralSecurityException;

public interface SalesHistoryQueryService {
    Page<SalesHistorySelectDTO> selectAllSalesHistoryByEmployee(SalesHistorySelectDTO salesHistorySelectDTO, Pageable pageable);

    SalesHistorySelectDTO selectSalesHistoryDetail(SalesHistorySelectDTO salesHistorySelectDTO);

    Page<SalesHistorySelectDTO> selectAllSalesHistory(Pageable pageable);

    SalesHistoryStatisticsDTO selectStatisticsByEmployee(SalesHistorySelectDTO salesHistorySelectDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchMonthByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);

    SalesHistoryStatisticsDTO selectStatisticsSearchYearByEmployee(SalesHistorySearchDTO salesHistorySearchDTO);
}
