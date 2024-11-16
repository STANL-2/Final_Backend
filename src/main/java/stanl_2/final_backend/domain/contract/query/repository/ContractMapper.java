package stanl_2.final_backend.domain.contract.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.center.common.util.RequestList;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContractMapper {
    ContractSeletIdDTO findContractByIdAndMemId(ContractSeletIdDTO contractDTO);

    List<Map<String, Object>> findContractBySearch(Map<String, Object> paramMap);

    int findContractBySearchCount(Map<String, Object> paramMap);

    List<Map<String, Object>> findContractAllByMemId(Map<String, Object> params);

    int findContractCountByMemId(String memId);
}
