package stanl_2.final_backend.domain.sales_history.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;

import java.security.GeneralSecurityException;

public interface SalesHistoryQueryService {
    Page<SalesHistorySelectDTO> selectAllSalesHistory(SalesHistorySelectDTO salesHistorySelectDTO, Pageable pageable) throws GeneralSecurityException;
}
