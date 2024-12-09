package stanl_2.final_backend.domain.contract.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.contract.query.dto.ContractExcelDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.util.List;

@Mapper
public interface ContractMapper {
    ContractSeletIdDTO findContractByIdAndMemId(@Param("contractId") String contractId,
                                                @Param("memberId") String memberId);

    List<ContractSearchDTO> findContractBySearchAndMemberId(@Param("offset") int offset,
                                                            @Param("pageSize") int pageSize,
                                                            @Param("contractSearchDTO") ContractSearchDTO contractSearchDTO,
                                                            @Param("sortField") String sortField,
                                                            @Param("sortOrder") String sortOrder);

    int findContractBySearchAndMemberIdCount(@Param("contractSearchDTO") ContractSearchDTO contractSearchDTO);

    List<ContractSelectAllDTO> findContractAllByMemId(@Param("offset") int offset,
                                                      @Param("pageSize") int pageSize,
                                                      @Param("memberId") String memberId);

    int findContractCountByMemId(String memberId);

    List<ContractSelectAllDTO> findContractAll(@Param("offset") int offset,
                                               @Param("pageSize") int pageSize,
                                               @Param("sortField") String sortField,
                                               @Param("sortOrder") String sortOrder);

    int findContractCount();

    ContractSeletIdDTO findContractById(String contractId);

    List<ContractSearchDTO> findContractBySearch(@Param("offset") int offset,
                                                 @Param("pageSize") int pageSize,
                                                 @Param("contractSearchDTO") ContractSearchDTO contractSearchDTO,
                                                 @Param("sortField") String sortField,
                                                 @Param("sortOrder") String sortOrder);

    int findContractBySearchCount(@Param("contractSearchDTO") ContractSearchDTO contractSearchDTO);

    List<ContractSelectAllDTO> findContractAllByCenterId(@Param("offset") int offset,
                                                         @Param("pageSize") int pageSize,
                                                         @Param("centerId") String centerId);

    Integer findContractCountByCenterId(@Param("centerId") String centerId);

    ContractSeletIdDTO findContractByIdAndCenterId(@Param("contractId")String contractId,
                                                   @Param("centerId") String centerId);

    List<ContractSearchDTO> findContractBySearchAndCenterId(@Param("offset") int offset,
                                                            @Param("pageSize") int pageSize,
                                                            @Param("contractSearchDTO") ContractSearchDTO contractSearchDTO,
                                                            @Param("centerId") String centerId,
                                                            @Param("sortField") String sortField,
                                                            @Param("sortOrder") String sortOrder);

    Integer findContractBySearchAndCenterCount(@Param("contractSearchDTO") ContractSearchDTO contractSearchDTO, @Param("centerId") String centerId);

    List<ContractExcelDTO> findContractForExcel();
}
