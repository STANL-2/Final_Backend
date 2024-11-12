package stanl_2.final_backend.domain.contract.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.contract.query.dto.ContractDTO;

@Mapper
public interface ContractMapper {
    ContractDTO findContractByIdAndMemId(ContractDTO contractDTO);
}
