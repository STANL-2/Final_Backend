package stanl_2.final_backend.domain.contract.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.command.application.dto.request.ContractRegistRequestDTO;
import stanl_2.final_backend.domain.contract.command.application.service.ContractService;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.contract.command.domain.repository.ContractRepository;

@Service("contractServiceImpl")
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    public ContractServiceImpl(ContractRepository contractRepository, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void registerContract(ContractRegistRequestDTO contractRegistRequestDTO) {
        // 회원인지 확인여부

        Contract contract = modelMapper.map(contractRegistRequestDTO, Contract.class);

        contract.setCentId("CEN_000000001");
        contract.setProdId("PRO_000000001");

        contractRepository.save(contract);
    }
}
