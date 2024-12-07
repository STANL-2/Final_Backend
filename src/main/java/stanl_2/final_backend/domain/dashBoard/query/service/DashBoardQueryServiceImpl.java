package stanl_2.final_backend.domain.dashBoard.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDTO;
import stanl_2.final_backend.domain.dashBoard.query.repository.DashBoardMapper;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;
import stanl_2.final_backend.domain.order.query.service.OrderQueryService;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

@Slf4j
@Service("queryDashBoardService")
public class DashBoardQueryServiceImpl implements DashBoardQueryService {

    private final DashBoardMapper dashBoardMapper;
    private final ContractQueryService contractQueryService;
    private final OrderQueryService orderQueryService;
    private final PurchaseOrderQueryService queryService;
    private final SalesHistoryQueryService salesHistoryQueryService;
    private final CustomerQueryService customerQueryService;
    private final NoticeService noticeService;

    @Autowired
    public DashBoardQueryServiceImpl(DashBoardMapper dashBoardMapper, ContractQueryService contractQueryService,
                                     OrderQueryService orderQueryService, PurchaseOrderQueryService queryService,
                                     SalesHistoryQueryService salesHistoryQueryService, CustomerQueryService customerQueryService,
                                     NoticeService noticeService) {
        this.dashBoardMapper = dashBoardMapper;
        this.contractQueryService = contractQueryService;
        this.orderQueryService = orderQueryService;
        this.queryService = queryService;
        this.salesHistoryQueryService = salesHistoryQueryService;
        this.customerQueryService = customerQueryService;
        this.noticeService = noticeService;
    }

    @Override
    public DashBoardDTO selectAllInfo(String memberLoginId) {

    }
}
