package stanl_2.final_backend.domain.contract.query.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpdateHistoryMapper {
    String selectUpdateHistoryByContractId(String contractId);
}
