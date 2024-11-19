package stanl_2.final_backend.domain.sales_history.command.application.service;

import stanl_2.final_backend.domain.sales_history.command.application.dto.SalesHistoryRegistDTO;
import stanl_2.final_backend.domain.sales_history.command.domain.repository.SalesHistoryRepository;

public interface SalesHistoryCommandService {

    void registerSalesHistory(String contractId);

    void deleteSalesHistory(String id);
}
