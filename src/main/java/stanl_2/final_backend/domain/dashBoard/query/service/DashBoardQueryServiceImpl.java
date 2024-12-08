package stanl_2.final_backend.domain.dashBoard.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardAdminDTO;
import stanl_2.final_backend.domain.dashBoard.query.repository.DashBoardMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;
import stanl_2.final_backend.domain.order.query.service.OrderQueryService;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryRankedDataDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final MemberQueryService memberQueryService;

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public DashBoardQueryServiceImpl(DashBoardMapper dashBoardMapper, AuthQueryService authQueryService,
                                     ContractQueryService contractQueryService, OrderQueryService orderQueryService,
                                     PurchaseOrderQueryService purchaseOrderQueryService, SalesHistoryQueryService salesHistoryQueryService,
                                     CustomerQueryService customerQueryService, NoticeService noticeService,
                                     MemberQueryService memberQueryService) {
        this.dashBoardMapper = dashBoardMapper;
        this.authQueryService = authQueryService;
        this.contractQueryService = contractQueryService;
        this.orderQueryService = orderQueryService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
        this.salesHistoryQueryService = salesHistoryQueryService;
        this.customerQueryService = customerQueryService;
        this.noticeService = noticeService;
        this.memberQueryService = memberQueryService;
    }


    @Override
    @Transactional(readOnly = true)
    public DashBoardAdminDTO selectInfoForAdmin(String memberLoginId) throws GeneralSecurityException {

        DashBoardAdminDTO dashBoardAdminDTO = new DashBoardAdminDTO();

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);
        String centerId = memberQueryService.selectMemberInfo(memberLoginId).getCenterId();
        System.out.println("centerId = " + centerId);


        // Pageable을 null로 전달하거나 유효한 Pageable 사용
        Pageable pageable = PageRequest.of(0, 100);
        // 이번달 조회를 위한 날짜 지정
        String startAt = getCurrentTime().substring(0,7) + "-01";
        String endAt = getCurrentTime().substring(0,10);

        // 이번달 Contract 받아오기
        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setMemberId(memberLoginId);
        contractSearchDTO.setSearchMemberId(memberId);
        contractSearchDTO.setStartAt(startAt);
        contractSearchDTO.setEndAt(endAt);
        contractSearchDTO.setActive(true);
        Integer unreadContract = Math.toIntExact(contractQueryService.selectBySearchEmployee(contractSearchDTO, pageable).getTotalElements());
        dashBoardAdminDTO.setUnreadContract(unreadContract);
        System.out.println("unreadContract" + unreadContract);

        // 이번달 Order 받아오기
        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setMemberId(memberLoginId);
        orderSelectSearchDTO.setStartDate(startAt);
        orderSelectSearchDTO.setEndDate(endAt);
        Integer unreadOrder = Math.toIntExact(orderQueryService.selectSearchOrdersEmployee(orderSelectSearchDTO, pageable).getTotalElements());
        dashBoardAdminDTO.setUnreadOrder(unreadOrder);
        System.out.println("unreadOrder" + unreadOrder);

        // 이번달 PurchaseOrder 받아오기
        PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO = new PurchaseOrderSelectSearchDTO();
        purchaseOrderSelectSearchDTO.setMemberId(memberLoginId);
        purchaseOrderSelectSearchDTO.setSearchMemberId(memberId);
        purchaseOrderSelectSearchDTO.setStartDate(startAt);
        purchaseOrderSelectSearchDTO.setEndDate(endAt);
        log.info("=====================2");
        Integer unreadPurchaseOrder = Math.toIntExact(purchaseOrderQueryService.selectSearchPurchaseOrderAdmin(purchaseOrderSelectSearchDTO, pageable).getTotalElements());
        log.info("=====================2");
        dashBoardAdminDTO.setUnreadPurchaseOrder(unreadPurchaseOrder);

        // 이번달 판매내역 받아오기
        ArrayList memberList = new ArrayList();
        memberList.add(memberId);

        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();
        salesHistorySearchDTO.setMemberList(memberList);
        salesHistorySearchDTO.setStartDate(startAt);
        salesHistorySearchDTO.setEndDate(endAt);
        log.info("=====================3");
        Page<SalesHistorySelectDTO> resultPage = salesHistoryQueryService.selectSalesHistorySearchByEmployee(salesHistorySearchDTO, pageable);
        Integer totalPrice = resultPage.getContent().isEmpty() ? null : resultPage.getContent().get(0).getSalesHistoryTotalSales();
        dashBoardAdminDTO.setTotalPrice(totalPrice);

        // 이번달 내 고객 순위 조회

        // 이번달 판매사원 순위
        ArrayList employeeList = new ArrayList();
        ArrayList centerList = new ArrayList();
        centerList.add(centerId);

        SalesHistoryRankedDataDTO salesHistoryRankedDataDTO = new SalesHistoryRankedDataDTO();
        salesHistoryRankedDataDTO.setCenterList(centerList);
        salesHistoryRankedDataDTO.setPeriod("month");
        salesHistoryRankedDataDTO.setStartDate(startAt);
        salesHistoryRankedDataDTO.setEndDate(endAt);
        salesHistoryRankedDataDTO.setGroupBy("employee");
        salesHistoryRankedDataDTO.setOrderBy("totalSales");
        log.info("=====================4");
        Page<SalesHistoryRankedDataDTO> rankPage = salesHistoryQueryService.selectStatisticsBySearch(salesHistoryRankedDataDTO, pageable);

        List<SalesHistoryRankedDataDTO> contentList = rankPage.getContent();
        for(SalesHistoryRankedDataDTO dto: contentList){
            if (dto.getMemberList() != null) {
                // 0번부터 4번 인덱스까지의 memberName을 추출
                for (int i = 0; i < dto.getMemberList().size() && i < 5; i++) {
                    employeeList.add(memberQueryService.selectNameById(dto.getMemberList().get(i)));
                }
            }
        }
        dashBoardAdminDTO.setMemberList(employeeList);

        // 공지사항 조회 (제목, 내용(redirect))
        ArrayList<Map<String, String>> noticeList = new ArrayList<>();

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTag("ADMIN");
        Page<NoticeDTO> noticePage = noticeService.findNotices(pageable, searchDTO);

        for (NoticeDTO notice : noticePage.getContent()) {
            Map<String, String> noticeData = new HashMap<>();
            noticeData.put("title", notice.getTitle()); // NoticeDTO의 title
            noticeData.put("content", notice.getContent()); // NoticeDTO의 content
            noticeList.add(noticeData);
        }
        dashBoardAdminDTO.setNoticeList(noticeList);
        log.info("=====================5");

        return dashBoardAdminDTO;
    }

//    @Override
//    public DashBoardAdminDTO selectInfoForAdmin(String memberLoginId) {
//        return null;
//    }
}
