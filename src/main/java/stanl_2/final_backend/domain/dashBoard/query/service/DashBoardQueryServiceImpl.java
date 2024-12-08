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
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDirectorDTO;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardEmployeeDTO;
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
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryStatisticsDTO;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDayDTO;
import stanl_2.final_backend.domain.schedule.query.service.ScheduleQueryService;

import java.security.GeneralSecurityException;
import java.time.LocalDate;
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

    private final AuthQueryService authQueryService;
    private final ContractQueryService contractQueryService;
    private final OrderQueryService orderQueryService;
    private final PurchaseOrderQueryService purchaseOrderQueryService;
    private final SalesHistoryQueryService salesHistoryQueryService;
    private final ScheduleQueryService scheduleQueryService;
    private final NoticeService noticeService;
    private final MemberQueryService memberQueryService;

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public DashBoardQueryServiceImpl(AuthQueryService authQueryService, ContractQueryService contractQueryService,
                                     OrderQueryService orderQueryService, PurchaseOrderQueryService purchaseOrderQueryService,
                                     SalesHistoryQueryService salesHistoryQueryService, ScheduleQueryService scheduleQueryService,
                                     NoticeService noticeService, MemberQueryService memberQueryService) {
        this.authQueryService = authQueryService;
        this.contractQueryService = contractQueryService;
        this.orderQueryService = orderQueryService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
        this.salesHistoryQueryService = salesHistoryQueryService;
        this.scheduleQueryService = scheduleQueryService;
        this.noticeService = noticeService;
        this.memberQueryService = memberQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public DashBoardEmployeeDTO selectInfoForEmployee(String memberLoginId) throws GeneralSecurityException {

        DashBoardEmployeeDTO dashBoardEmployeeDTO = new DashBoardEmployeeDTO();

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);
        String centerId = memberQueryService.selectMemberInfo(memberLoginId).getCenterId();

        // Pageable을 null로 전달하거나 유효한 Pageable 사용
        Pageable pageable = PageRequest.of(0, 100);

        String startAt = getCurrentTime().substring(0, 7) + "-01";
        String endAt = getCurrentTime().substring(0,10);

        LocalDate date = LocalDate.parse(endAt);
        LocalDate nextDate = date.plusDays(1);
        // 프론트에서 +1 처리했기 떄문에 따로 백에서 처리
        String salesEndAt = nextDate.toString();


        // 이번달 Contract 받아오기
        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setMemberId(memberLoginId);
        contractSearchDTO.setSearchMemberId(memberId);
        contractSearchDTO.setStartDate(startAt);
        contractSearchDTO.setEndDate(endAt);
        Integer unreadContract = Math.toIntExact(contractQueryService.selectBySearchEmployee(contractSearchDTO, pageable).getTotalElements());
        dashBoardEmployeeDTO.setUnreadContract(unreadContract);

        // 이번달 Order 받아오기
        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setMemberId(memberLoginId);
        orderSelectSearchDTO.setStartDate(startAt);
        orderSelectSearchDTO.setEndDate(endAt);
        Integer unreadOrder = Math.toIntExact(orderQueryService.selectSearchOrdersEmployee(orderSelectSearchDTO, pageable).getTotalElements());
        dashBoardEmployeeDTO.setUnreadOrder(unreadOrder);

        // 이번달 판매내역 받아오기
        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();
        salesHistorySearchDTO.setSearcherName(memberLoginId);
        salesHistorySearchDTO.setStartDate(startAt);
        salesHistorySearchDTO.setEndDate(salesEndAt);

        SalesHistoryStatisticsDTO resultStatistics = salesHistoryQueryService.selectStatisticsSearchByEmployee(salesHistorySearchDTO);
        Integer totalPrice = resultStatistics.getTotalSales();
        dashBoardEmployeeDTO.setTotalPrice(totalPrice);

        // 오늘 일정조회
        ArrayList scheduleList = new ArrayList();

        List<ScheduleDayDTO> todaySchedules = scheduleQueryService.findSchedulesByDate(endAt);

        for (ScheduleDayDTO schedule : todaySchedules) {
            scheduleList.add(schedule.getName());
        }
        dashBoardEmployeeDTO.setScheduleTitle(scheduleList);

        // 이번달 판매사원 순위
        ArrayList employeeList = new ArrayList();
        ArrayList centerList = new ArrayList();
        centerList.add(centerId);

        SalesHistoryRankedDataDTO salesHistoryRankedDataDTO = new SalesHistoryRankedDataDTO();
        salesHistoryRankedDataDTO.setCenterList(centerList);
        salesHistoryRankedDataDTO.setPeriod("month");
        salesHistoryRankedDataDTO.setStartDate(startAt);
        salesHistoryRankedDataDTO.setEndDate(salesEndAt);
        salesHistoryRankedDataDTO.setGroupBy("employee");
        salesHistoryRankedDataDTO.setOrderBy("totalSales");

        Page<SalesHistoryRankedDataDTO> rankPage = salesHistoryQueryService.selectStatisticsBySearch(salesHistoryRankedDataDTO, pageable);

        List<SalesHistoryRankedDataDTO> contentList = rankPage.getContent();

        for (int i = 0; i < Math.min(contentList.size(), 5); i++) {
            employeeList.add(contentList.get(i).getMemberId());
        }
        dashBoardEmployeeDTO.setMemberList(employeeList);

        // 공지사항 조회 (제목, 내용(redirect))
        ArrayList<Map<String, String>> noticeList = new ArrayList<>();

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTag("ALL");
        Page<NoticeDTO> noticePage = noticeService.findNotices(pageable, searchDTO);

        for (NoticeDTO notice : noticePage.getContent()) {
            Map<String, String> noticeData = new HashMap<>();
            noticeData.put("title", notice.getTitle()); // NoticeDTO의 title
            noticeData.put("content", notice.getContent()); // NoticeDTO의 content
            noticeList.add(noticeData);
        }
        dashBoardEmployeeDTO.setNoticeList(noticeList);

        return dashBoardEmployeeDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public DashBoardAdminDTO selectInfoForAdmin(String memberLoginId) throws GeneralSecurityException {

        DashBoardAdminDTO dashBoardAdminDTO = new DashBoardAdminDTO();

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);
        String centerId = memberQueryService.selectMemberInfo(memberLoginId).getCenterId();

        // Pageable을 null로 전달하거나 유효한 Pageable 사용
        Pageable pageable = PageRequest.of(0, 100);

        String startAt = getCurrentTime().substring(0, 7) + "-01";
        String endAt = getCurrentTime().substring(0,10);

        LocalDate date = LocalDate.parse(endAt);
        LocalDate nextDate = date.plusDays(1);
        // 프론트에서 +1 처리했기 떄문에 따로 백에서 처리
        String salesEndAt = nextDate.toString();

        // 이번달 Contract 받아오기
        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setMemberId(memberLoginId);
        contractSearchDTO.setSearchMemberId(memberId);
        contractSearchDTO.setStartDate(startAt);
        contractSearchDTO.setEndDate(endAt);
        Integer unreadContract = Math.toIntExact(contractQueryService.selectBySearchEmployee(contractSearchDTO, pageable).getTotalElements());
        dashBoardAdminDTO.setUnreadContract(unreadContract);

        // 이번달 Order 받아오기
        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setMemberId(memberLoginId);
        orderSelectSearchDTO.setStartDate(startAt);
        orderSelectSearchDTO.setEndDate(endAt);
        Integer unreadOrder = Math.toIntExact(orderQueryService.selectSearchOrdersEmployee(orderSelectSearchDTO, pageable).getTotalElements());
        dashBoardAdminDTO.setUnreadOrder(unreadOrder);

        // 이번달 PurchaseOrder 받아오기
        PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO = new PurchaseOrderSelectSearchDTO();
        purchaseOrderSelectSearchDTO.setMemberId(memberLoginId);
        purchaseOrderSelectSearchDTO.setSearchMemberId(memberId);
        purchaseOrderSelectSearchDTO.setStartDate(startAt);
        purchaseOrderSelectSearchDTO.setEndDate(endAt);
        Integer unreadPurchaseOrder = Math.toIntExact(purchaseOrderQueryService.selectSearchPurchaseOrderAdmin(purchaseOrderSelectSearchDTO, pageable).getTotalElements());
        dashBoardAdminDTO.setUnreadPurchaseOrder(unreadPurchaseOrder);

        // 이번달 판매내역 받아오기
        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();
        salesHistorySearchDTO.setSearcherName(memberLoginId);
        salesHistorySearchDTO.setStartDate(startAt);
        salesHistorySearchDTO.setEndDate(salesEndAt);

        SalesHistoryStatisticsDTO resultStatistics = salesHistoryQueryService.selectStatisticsSearchByEmployee(salesHistorySearchDTO);
        Integer totalPrice = resultStatistics.getTotalSales();
        dashBoardAdminDTO.setTotalPrice(totalPrice);

        // 이번달 판매사원 순위
        ArrayList employeeList = new ArrayList();
        ArrayList centerList = new ArrayList();
        centerList.add(centerId);

        SalesHistoryRankedDataDTO salesHistoryRankedDataDTO = new SalesHistoryRankedDataDTO();
        salesHistoryRankedDataDTO.setCenterList(centerList);
        salesHistoryRankedDataDTO.setPeriod("month");
        salesHistoryRankedDataDTO.setStartDate(startAt);
        salesHistoryRankedDataDTO.setEndDate(salesEndAt);
        salesHistoryRankedDataDTO.setGroupBy("employee");
        salesHistoryRankedDataDTO.setOrderBy("totalSales");

        Page<SalesHistoryRankedDataDTO> rankPage = salesHistoryQueryService.selectStatisticsBySearch(salesHistoryRankedDataDTO, pageable);

        List<SalesHistoryRankedDataDTO> contentList = rankPage.getContent();

        for (int i = 0; i < Math.min(contentList.size(), 5); i++) {
            employeeList.add(contentList.get(i).getMemberId());
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

        return dashBoardAdminDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public DashBoardDirectorDTO selectInfoForDirector(String memberLoginId) throws GeneralSecurityException {

        DashBoardDirectorDTO dashBoardDirectorDTO = new DashBoardDirectorDTO();

        // Pageable을 null로 전달하거나 유효한 Pageable 사용
        Pageable pageable = PageRequest.of(0, 100);

        String startAt = getCurrentTime().substring(0, 7) + "-01";
        String endAt = getCurrentTime().substring(0,10);

        LocalDate date = LocalDate.parse(endAt);
        LocalDate nextDate = date.plusDays(1);
        // 프론트에서 +1 처리했기 떄문에 따로 백에서 처리
        String salesEndAt = nextDate.toString();

        // 승인되지 않은 발주서 건수
        PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO = new PurchaseOrderSelectSearchDTO();
        purchaseOrderSelectSearchDTO.setMemberId(memberLoginId);
        purchaseOrderSelectSearchDTO.setStatus("WAIT");
        purchaseOrderSelectSearchDTO.setStartDate(startAt);
        purchaseOrderSelectSearchDTO.setEndDate(endAt);
        Integer unreadPurchaseOrderCnt = Math.toIntExact(purchaseOrderQueryService.selectSearchPurchaseOrder(purchaseOrderSelectSearchDTO, pageable).getTotalElements());
        dashBoardDirectorDTO.setUnreadPurchaseOrder(unreadPurchaseOrderCnt);

        // 판매 매장 실적 순위
        ArrayList centerList = new ArrayList();

        SalesHistoryRankedDataDTO salesHistoryRankedDataDTO = new SalesHistoryRankedDataDTO();
        salesHistoryRankedDataDTO.setPeriod("month");
        salesHistoryRankedDataDTO.setStartDate(startAt);
        salesHistoryRankedDataDTO.setEndDate(salesEndAt);

        salesHistoryRankedDataDTO.setGroupBy("center");
        salesHistoryRankedDataDTO.setOrderBy("totalSales");

        Page<SalesHistoryRankedDataDTO> rankPage = salesHistoryQueryService.selectStatisticsBySearch(salesHistoryRankedDataDTO, pageable);

        List<SalesHistoryRankedDataDTO> content = rankPage.getContent();

        for (int i = 0; i < Math.min(content.size(), 5); i++) {
            centerList.add(content.get(i).getCenterId());
        }

        dashBoardDirectorDTO.setCenterList(centerList);

        return dashBoardDirectorDTO;
    }
}
