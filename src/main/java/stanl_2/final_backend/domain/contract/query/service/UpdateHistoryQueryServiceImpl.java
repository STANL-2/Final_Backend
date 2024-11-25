package stanl_2.final_backend.domain.contract.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.query.repository.UpdateHistoryMapper;

@Service
public class UpdateHistoryQueryServiceImpl implements UpdateHistoryQueryService {

    private final UpdateHistoryMapper updateHistoryMapper;

    @Autowired
    public UpdateHistoryQueryServiceImpl(UpdateHistoryMapper updateHistoryMapper) {
        this.updateHistoryMapper = updateHistoryMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public String selectUpdateHistoryByContractId(String contractId) {

        String content = updateHistoryMapper.selectUpdateHistoryByContractId(contractId);

        return content;
    }
}
