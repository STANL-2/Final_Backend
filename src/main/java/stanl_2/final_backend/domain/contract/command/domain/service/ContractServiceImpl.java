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
        // 회원인지 확인여부 및 값 가져오기
        
        // 일련번호로 제품테이블의 총식별번호 찾아서 제품 가져오기

        // 고객전화번호로 고객테이블 찾아서 고객이 있으면 넘어가고, 고객이 없으면 고객테이블에 고객 정보 넣기

        Contract contract = modelMapper.map(contractRegistRequestDTO, Contract.class);

        contract.setCentId("CEN_000000001");    // 회원의 매장번호 넣기
        contract.setProdId("PRO_000000001");    // 제품 번호 넣기

        contractRepository.save(contract);
    }
}
