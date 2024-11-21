package stanl_2.final_backend.domain.contract.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.util.List;

@Mapper
public interface ContractMapper {
    ContractSeletIdDTO findContractByIdAndMemId(@Param("purchaseOrderId") String purchaseOrderId,
                                                @Param("memberId") String memberId);

    List<ContractSearchDTO> findContractBySearchAndMemberId(@Param("offset") int offset,
                                                            @Param("pageSize") int pageSize,
                                                            @Param("contractSearchDTO") ContractSearchDTO contractSearchDTO);

    int findContractBySearchAndMemberIdCount(ContractSearchDTO contractSearchDTO);

    List<ContractSelectAllDTO> findContractAllByMemId(@Param("offset") int offset,
                                                      @Param("pageSize") int pageSize,
                                                      @Param("memberId") String memberId);

    int findContractCountByMemId(String memId);

    List<ContractSelectAllDTO> findContractAll(@Param("offset") int offset,
                                               @Param("pageSize") int pageSize);

    int findContractCount();

    ContractSeletIdDTO findContractById(String contractId);

    List<ContractSearchDTO> findContractBySearch(@Param("offset") int offset,
                                                 @Param("pageSize") int pageSize,
                                                 @Param("contractSearchDTO") ContractSearchDTO contractSearchDTO);

    int findContractBySearchCount(ContractSearchDTO contractSearchDTO);
}
