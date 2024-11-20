package stanl_2.final_backend.domain.product.command.application.command.service;

import stanl_2.final_backend.domain.contract.command.application.dto.ContractDeleteDTO;

public interface ProductCommandService {
    void modifyProductStock(String productId);
}
