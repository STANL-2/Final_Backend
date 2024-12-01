package stanl_2.final_backend.domain.sales_history.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;
import stanl_2.final_backend.domain.sales_history.command.application.dto.SalesHistoryRegistDTO;
import stanl_2.final_backend.domain.sales_history.command.application.service.SalesHistoryCommandService;
import stanl_2.final_backend.domain.sales_history.command.domain.aggregate.entity.SalesHistory;
import stanl_2.final_backend.domain.sales_history.command.domain.repository.SalesHistoryRepository;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SalesHistoryCommandServiceImpl implements SalesHistoryCommandService {

    private final SalesHistoryRepository salesHistoryRepository;
    private final ContractQueryService contractQueryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SalesHistoryCommandServiceImpl(SalesHistoryRepository salesHistoryRepository, ContractQueryService contractQueryService, ModelMapper modelMapper) {
        this.salesHistoryRepository = salesHistoryRepository;
        this.contractQueryService = contractQueryService;
        this.modelMapper = modelMapper;
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerSalesHistory(String contractId) {

        ContractSeletIdDTO salesHistoryDTO = new ContractSeletIdDTO();
        SalesHistoryRegistDTO salesHistoryRegistDTO = new SalesHistoryRegistDTO();

        salesHistoryDTO.setContractId(contractId);

        ContractSeletIdDTO responseContract = contractQueryService.selectDetailContract(salesHistoryDTO);

        if (responseContract == null) {
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.CONTRACT_NOT_FOUND);
        }

        salesHistoryRegistDTO.setContractId(contractId);
        salesHistoryRegistDTO.setTotalSales(responseContract.getTotalSales());
        salesHistoryRegistDTO.setNumberOfVehicles(responseContract.getNumberOfVehicles());
        salesHistoryRegistDTO.setCustomerInfoId(responseContract.getCustomerId());
        salesHistoryRegistDTO.setProductId(responseContract.getProductId());
        salesHistoryRegistDTO.setCenterId(responseContract.getCenterId());
        salesHistoryRegistDTO.setMemberId(responseContract.getMemberId());

        String customerPurchaseCondition = responseContract.getCustomerPurchaseCondition();

        if(customerPurchaseCondition.equals("CASH")){
            salesHistoryRegistDTO.setIncentive(responseContract.getTotalSales() * 0.035);
        }else if(customerPurchaseCondition.equals("INSTALLMENT")){
            salesHistoryRegistDTO.setIncentive(responseContract.getTotalSales() * 0.04);
        }else if(customerPurchaseCondition.equals("LEASE")){
            salesHistoryRegistDTO.setIncentive(responseContract.getTotalSales() * 0.045);
        }

        SalesHistory salesHistory = modelMapper.map(salesHistoryRegistDTO, SalesHistory.class);

        salesHistoryRepository.save(salesHistory);
    }

    @Override
    @Transactional
    public void deleteSalesHistory(String id) {
        SalesHistory salesHistory = salesHistoryRepository.findById(id)
                .orElseThrow(() -> new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND));

        salesHistory.setActive(false);
        salesHistory.setDeletedAt(getCurrentTime());

        salesHistoryRepository.save(salesHistory);
    }
}
