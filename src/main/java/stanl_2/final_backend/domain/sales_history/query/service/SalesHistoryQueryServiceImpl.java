package stanl_2.final_backend.domain.sales_history.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.query.service.CenterQueryService;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;
import stanl_2.final_backend.domain.sales_history.query.dto.*;
import stanl_2.final_backend.domain.sales_history.query.repository.SalesHistoryMapper;
import org.springframework.data.redis.core.RedisTemplate;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.List;

@Service
public class SalesHistoryQueryServiceImpl implements SalesHistoryQueryService {

    private final SalesHistoryMapper salesHistoryMapper;
    private final MemberQueryService memberQueryService;
    private final AuthQueryService authQueryService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CenterQueryService centerQueryService;
    private final CustomerQueryService customerQueryService;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public SalesHistoryQueryServiceImpl(SalesHistoryMapper salesHistoryMapper, MemberQueryService memberQueryService, AuthQueryService authQueryService, RedisTemplate<String, Object> redisTemplate, CenterQueryService centerQueryService, CustomerQueryService customerQueryService, ExcelUtilsV1 excelUtilsV1) {
        this.salesHistoryMapper = salesHistoryMapper;
        this.memberQueryService = memberQueryService;
        this.authQueryService = authQueryService;
        this.redisTemplate = redisTemplate;
        this.centerQueryService = centerQueryService;
        this.customerQueryService = customerQueryService;
        this.excelUtilsV1 = excelUtilsV1;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistorySelectDTO> selectAllSalesHistoryByEmployee(SalesHistorySelectDTO salesHistorySelectDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        String searcherId = authQueryService.selectMemberIdByLoginId(salesHistorySelectDTO.getSearcherName());

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistoryByEmployee(size,offset, searcherId, sortField, sortOrder);

        int total = salesHistoryMapper.findSalesHistoryCountByEmployee(searcherId);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                salesHistory.setCustomerId(customerQueryService.selectCustomerNameById(salesHistory.getCustomerId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CUSTOMER_NOT_FOUND);
            }
            try {
                salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }


    @Override
    @Transactional(readOnly = true)
    public SalesHistorySelectDTO selectSalesHistoryDetail(SalesHistorySelectDTO salesHistorySelectDTO) {

        SalesHistorySelectDTO salesHistoryDetailDTO = salesHistoryMapper.findSalesHistoryDetail(salesHistorySelectDTO);

        if(salesHistoryDetailDTO == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return salesHistoryDetailDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistorySelectDTO> selectSalesHistorySearchByEmployee(SalesHistorySearchDTO salesHistorySearchDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistorySearchByEmployee(size,offset, salesHistorySearchDTO, sortField, sortOrder);

        int total = salesHistoryMapper.findSalesHistorySearchCountByEmployee(salesHistorySearchDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                salesHistory.setCustomerId(customerQueryService.selectCustomerNameById(salesHistory.getCustomerId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CUSTOMER_NOT_FOUND);
            }
            try {
                salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    public Page<SalesHistorySelectDTO> selectSalesHistoryBySearch(SalesHistorySearchDTO salesHistorySearchDTO, Pageable pageable) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        if(salesHistorySearchDTO.getCustomerName() != null){
            salesHistorySearchDTO.setCustomerList(customerQueryService.selectCustomerId(salesHistorySearchDTO.getCustomerName()));
        }

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistoryBySearch(size,offset, salesHistorySearchDTO, sortField, sortOrder);

        int total = salesHistoryMapper.findSalesHistoryCountBySearch(salesHistorySearchDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                salesHistory.setCustomerId(customerQueryService.selectCustomerNameById(salesHistory.getCustomerId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CUSTOMER_NOT_FOUND);
            }
            try {
                salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistorySelectDTO> selectAllSalesHistory(Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findAllSalesHistory(size,offset, sortField, sortOrder);

        int total = salesHistoryMapper.findSalesHistoryCount();

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                salesHistory.setCustomerId(customerQueryService.selectCustomerNameById(salesHistory.getCustomerId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CUSTOMER_NOT_FOUND);
            }
            try {
                salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public SalesHistoryStatisticsDTO selectStatisticsByEmployee(SalesHistorySelectDTO salesHistorySelectDTO) {

        salesHistorySelectDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySelectDTO.getSearcherName()));

        String cacheKey = "salesHistory::statistics::searcherId=" + salesHistorySelectDTO.getSearcherName();

        SalesHistoryStatisticsDTO salesHistoryStatisticsDTO = (SalesHistoryStatisticsDTO) redisTemplate.opsForValue().get(cacheKey);

        if (salesHistoryStatisticsDTO == null) {
            // 캐시에 데이터가 없을 경우 DB에서 조회
            salesHistoryStatisticsDTO = salesHistoryMapper.findStatisticsByEmployee(salesHistorySelectDTO);

            if(salesHistoryStatisticsDTO == null){
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
            }

            // Redis에 데이터 저장 /만료 시간 설정
            if (salesHistoryStatisticsDTO != null) {
                redisTemplate.opsForValue().set(cacheKey, salesHistoryStatisticsDTO, Duration.ofMinutes(10)); // 캐시 10분 유지
            }
        }
        return salesHistoryStatisticsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public SalesHistoryStatisticsDTO selectStatisticsSearchByEmployee(SalesHistorySearchDTO salesHistorySearchDTO) {

        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        SalesHistoryStatisticsDTO salesHistoryStatisticsDTO = salesHistoryMapper.findStatisticsSearchByEmployee(salesHistorySearchDTO);

        if(salesHistoryStatisticsDTO == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }
        return salesHistoryStatisticsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesHistoryStatisticsDTO> selectStatisticsSearchByEmployeeDaily(SalesHistorySearchDTO salesHistorySearchDTO) {

        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        List<SalesHistoryStatisticsDTO> salesHistoryStatisticsDTOList = salesHistoryMapper.findStatisticsSearchByEmployeeDaily(salesHistorySearchDTO);

        if(salesHistoryStatisticsDTOList == null || salesHistoryStatisticsDTOList.isEmpty()){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }
        return salesHistoryStatisticsDTOList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<SalesHistoryStatisticsDTO> selectStatisticsSearchMonthByEmployee(SalesHistorySearchDTO salesHistorySearchDTO) {
        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));


        String parseStartDate = salesHistorySearchDTO.getStartDate();
        String parseEndDate = salesHistorySearchDTO.getEndDate();

        salesHistorySearchDTO.setStartDate(parseStartDate.substring(0,7));
        salesHistorySearchDTO.setEndDate(parseEndDate.substring(0,7));

        List<SalesHistoryStatisticsDTO> salesHistoryStatisticsDTOList = salesHistoryMapper.findStatisticsSearchMonthByEmployee(salesHistorySearchDTO);

        if(salesHistoryStatisticsDTOList == null || salesHistoryStatisticsDTOList.isEmpty()){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }
        return salesHistoryStatisticsDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesHistoryStatisticsDTO> selectStatisticsSearchYearByEmployee(SalesHistorySearchDTO salesHistorySearchDTO) {
        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        String parseStartDate = salesHistorySearchDTO.getStartDate();
        String parseEndDate = salesHistorySearchDTO.getEndDate();

        parseStartDate = parseStartDate.substring(0,4);
        parseEndDate = parseEndDate.substring(0,4);

        salesHistorySearchDTO.setStartDate(parseStartDate);
        salesHistorySearchDTO.setEndDate(parseEndDate);

        List<SalesHistoryStatisticsDTO> salesHistoryStatisticsDTOList = salesHistoryMapper.findStatisticsSearchYearByEmployee(salesHistorySearchDTO);

        if(salesHistoryStatisticsDTOList == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }
        return salesHistoryStatisticsDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistoryRankedDataDTO> selectStatistics(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findAllRank(salesHistoryRankedDataDTO, size,offset);

        int total = salesHistoryMapper.findRankCount();

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistoryStatisticsAverageDTO> selectStatisticsAverageBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryStatisticsAverageDTO> salesHistoryStatisticsAverageList = salesHistoryMapper.findStatisticsAverageBySearch(size,offset,salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsBySearchCount(salesHistoryRankedDataDTO);

        if(salesHistoryStatisticsAverageList == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryStatisticsAverageList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistoryRankedDataDTO> selectStatisticsBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsBySearch(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsBySearchCount(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                if(salesHistory.getMemberId() != null) {
                    salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
                }
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                if(salesHistory.getCenterId() != null) {
                    salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
                }
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional
    public void exportSalesHistoryToExcel(HttpServletResponse response) {
        List<SalesHistoryExcelDownload> salesHistoryList = salesHistoryMapper.findSalesHistoryForExcel();

        if(salesHistoryList == null) {
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                salesHistory.setCustomerId(customerQueryService.selectCustomerNameById(salesHistory.getCustomerId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CUSTOMER_NOT_FOUND);
            }
            try {
                salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        excelUtilsV1.download(SalesHistoryExcelDownload.class, salesHistoryList, "SalesHistoryExcel", response);

    }

    @Override
    @Transactional
    public String selectSalesHistoryIdByContractId(String contractId) {

        String salesHistoryId = salesHistoryMapper.findSalesHistoryIdByContractId(contractId);

        if(salesHistoryId == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return salesHistoryId;
    }

    @Override
    @Transactional
    public Page<SalesHistoryRankedDataDTO> selectStatisticsBestBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsBestBySearch(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsBySearchCount(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional
    public Page<SalesHistoryRankedDataDTO> selectAllStatstics(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable){
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> allStatistics= salesHistoryMapper.findAllStatisticsBySearch(size, offset, salesHistoryRankedDataDTO);



        if(allStatistics.isEmpty()){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(allStatistics, pageable, 0);
    }

    @Override
    public Page<SalesHistoryRankedDataDTO> selectMyStatisticsBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<String> memberList = new java.util.ArrayList<>(List.of());

        memberList.add(authQueryService.selectMemberIdByLoginId(salesHistoryRankedDataDTO.getMemberId()));

        salesHistoryRankedDataDTO.setMemberList(memberList);

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsBySearch(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsBySearchCount(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        salesHistoryList.forEach(salesHistory -> {
            try {
                if(salesHistory.getMemberId() != null) {
                    salesHistory.setMemberId(memberQueryService.selectNameById(salesHistory.getMemberId()));
                }
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
            try {
                if(salesHistory.getCenterId() != null) {
                    salesHistory.setCenterId(centerQueryService.selectNameById(salesHistory.getCenterId()));
                }
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(salesHistoryList, pageable, total);
    }
}
