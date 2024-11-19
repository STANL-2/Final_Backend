package stanl_2.final_backend.domain.sales_history.query.service;

import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectAllDTO;

public interface SalesHistoryQueryService {
    SalesHistorySelectAllDTO selectAllSalesHistory();
}
