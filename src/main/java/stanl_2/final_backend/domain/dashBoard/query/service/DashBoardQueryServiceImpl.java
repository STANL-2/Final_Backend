package stanl_2.final_backend.domain.dashBoard.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.dashBoard.common.exception.DashBoardCommonException;
import stanl_2.final_backend.domain.dashBoard.common.exception.DashBoardErrorCode;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDTO;
import stanl_2.final_backend.domain.dashBoard.query.repository.DashBoardMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;
import stanl_2.final_backend.domain.order.query.service.OrderQueryService;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleCommonException;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleErrorCode;

@Slf4j
@Service("queryDashBoardService")
public class DashBoardQueryServiceImpl implements DashBoardQueryService {

    private final DashBoardMapper dashBoardMapper;
    private final AuthQueryService authQueryService;
    private final ContractQueryService contractQueryService;
    private final OrderQueryService orderQueryService;
    private final PurchaseOrderQueryService queryService;
    private final SalesHistoryQueryService salesHistoryQueryService;
    private final CustomerQueryService customerQueryService;
    private final NoticeService noticeService;


    @Autowired
    public DashBoardQueryServiceImpl(DashBoardMapper dashBoardMapper, AuthQueryService authQueryService) {
        this.dashBoardMapper = dashBoardMapper;
        this.authQueryService = authQueryService;
    }

    @Override
    public DashBoardDTO selectAllInfo(String memberLoginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);

        DashBoardDTO responseDashBoardDTO  = dashBoardMapper.findDashBoardInfoByMemberId(memberId);

        if(responseDashBoardDTO == null){
            throw new DashBoardCommonException(DashBoardErrorCode.DATA_NOT_FOUND);
        }
        return responseDashBoardDTO;
    }
}
