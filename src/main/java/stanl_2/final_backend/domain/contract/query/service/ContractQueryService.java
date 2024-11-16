package stanl_2.final_backend.domain.contract.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.util.Map;

public interface ContractQueryService {
    ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractDTO);

    Page<Map<String, Object>> selectBySearch(Map<String, Object> paramMap);

    Page<Map<String, Object>> selectAll(String memId, Pageable pageable);
}
