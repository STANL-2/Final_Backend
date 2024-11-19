package stanl_2.final_backend.domain.contract.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContractMapper {
    ContractSeletIdDTO findContractByIdAndMemId(ContractSeletIdDTO contractDTO);

    List<ContractSearchDTO> findContractBySearch(Map<String, Object> map);

    int findContractBySearchCount(Map<String, Object> map);

    List<ContractSelectAllDTO> findContractAllByMemId(Map<String, Object> map);

    int findContractCountByMemId(String memId);
}
