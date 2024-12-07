package stanl_2.final_backend.domain.dashBoard.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.dashBoard.common.exception.DashBoardCommonException;
import stanl_2.final_backend.domain.dashBoard.common.exception.DashBoardErrorCode;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardAdminDTO;
import stanl_2.final_backend.domain.dashBoard.query.repository.DashBoardMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;
import stanl_2.final_backend.domain.order.query.service.OrderQueryService;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("queryDashBoardService")
public class DashBoardQueryServiceImpl implements DashBoardQueryService {

    private final DashBoardMapper dashBoardMapper;
    private final AuthQueryService authQueryService;
    private final ContractQueryService contractQueryService;
    private final OrderQueryService orderQueryService;
    private final PurchaseOrderQueryService purchaseOrderQueryService;
    private final SalesHistoryQueryService salesHistoryQueryService;
    private final CustomerQueryService customerQueryService;
    private final NoticeService noticeService;
    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public DashBoardQueryServiceImpl(DashBoardMapper dashBoardMapper, AuthQueryService authQueryService,
                                     ContractQueryService contractQueryService, OrderQueryService orderQueryService,
                                     PurchaseOrderQueryService purchaseOrderQueryService, SalesHistoryQueryService salesHistoryQueryService,
                                     CustomerQueryService customerQueryService, NoticeService noticeService) {
        this.dashBoardMapper = dashBoardMapper;
        this.authQueryService = authQueryService;
        this.contractQueryService = contractQueryService;
        this.orderQueryService = orderQueryService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
        this.salesHistoryQueryService = salesHistoryQueryService;
        this.customerQueryService = customerQueryService;
        this.noticeService = noticeService;
    }


    @Override
    @Transactional(readOnly = true)
    public DashBoardAdminDTO selectInfoForEmployee(String memberLoginId) {

        DashBoardAdminDTO dashBoardAdminDTO = new DashBoardAdminDTO();
        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);
        // Pageable을 null로 전달하거나 유효한 Pageable 사용
        Pageable pageable = Pageable.unpaged();
        // 이번달 조회를 위한 날짜 지정
        String startAt = getCurrentTime().substring(0,7) + "-01";
        String endAt = getCurrentTime().substring(0,9);


        // 이번달 Contract 받아오기
        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setMemberId(memberId);
        contractSearchDTO.setStartAt(startAt);
        contractSearchDTO.setEndAt(endAt);
        Integer unreadContract = contractQueryService.selectBySearchEmployee(contractSearchDTO, pageable).getNumberOfElements();
        dashBoardAdminDTO.setUnreadContract(unreadContract);


        // 이번달 Order 받아오기
        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setMemberId(memberId);
        orderSelectSearchDTO.setStartDate(startAt);
        orderSelectSearchDTO.setEndDate(endAt);
        Integer unreadOrder = orderQueryService.selectSearchOrdersEmployee(orderSelectSearchDTO, pageable).getNumberOfElements();
        dashBoardAdminDTO.setUnreadOrder(unreadOrder);

        // 이번달 판매내역 받아오기
        ArrayList memberList = new ArrayList();
        memberList.add(memberId);

        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();
        salesHistorySearchDTO.setMemberList(memberList);
        salesHistorySearchDTO.setStartDate(startAt);
        salesHistorySearchDTO.setEndDate(endAt);
        Page<SalesHistorySelectDTO> resultPage = salesHistoryQueryService.selectSalesHistorySearchByEmployee(salesHistorySearchDTO, pageable);
        Integer totalPrice = resultPage.getContent().isEmpty() ? null : resultPage.getContent().get(0).getSalesHistoryTotalSales();
        dashBoardAdminDTO.setTotalPrice(totalPrice);

        // 이번달 내 고객 순위 조회


        // 이번달 판매사원 순위


        // 공지사항

        DashBoardAdminDTO responseDashBoardAdminDTO = dashBoardMapper.findDashBoardInfoByMemberId(memberId);

        if(responseDashBoardAdminDTO == null){
            throw new DashBoardCommonException(DashBoardErrorCode.DATA_NOT_FOUND);
        }
        return responseDashBoardAdminDTO;
    }

    @Override
    public DashBoardAdminDTO selectInfoForAdmin(String memberLoginId) {
        return null;
    }
}
