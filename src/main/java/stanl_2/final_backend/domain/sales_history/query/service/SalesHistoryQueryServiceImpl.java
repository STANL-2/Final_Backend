package stanl_2.final_backend.domain.sales_history.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleExcelDownload;
import stanl_2.final_backend.domain.center.query.service.CenterQueryService;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationCommonException;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationErrorCode;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.product.common.exception.ProductCommonException;
import stanl_2.final_backend.domain.product.common.exception.ProductErrorCode;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;
import stanl_2.final_backend.domain.sales_history.query.dto.*;
import stanl_2.final_backend.domain.sales_history.query.repository.SalesHistoryMapper;
import org.springframework.data.redis.core.RedisTemplate;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

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

        String searcherId = authQueryService.selectMemberIdByLoginId(salesHistorySelectDTO.getSearcherName());

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistoryByEmployee(size,offset, searcherId);

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

        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistorySearchByEmployee(size,offset, salesHistorySearchDTO);

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
    public Page<SalesHistorySelectDTO> selectSalesHistoryBySearch(SalesHistorySearchDTO salesHistorySearchDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistoryBySearch(size,offset, salesHistorySearchDTO);

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

        List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findAllSalesHistory(size,offset);

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

        return salesHistoryStatisticsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public SalesHistoryStatisticsDTO selectStatisticsSearchMonthByEmployee(SalesHistorySearchDTO salesHistorySearchDTO) {
        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        SalesHistoryStatisticsDTO salesHistoryStatisticsDTO = salesHistoryMapper.findStatisticsSearchMonthByEmployee(salesHistorySearchDTO);

        if(salesHistoryStatisticsDTO == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }
        return salesHistoryStatisticsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public SalesHistoryStatisticsDTO selectStatisticsSearchYearByEmployee(SalesHistorySearchDTO salesHistorySearchDTO) {
        salesHistorySearchDTO.setSearcherName(authQueryService.selectMemberIdByLoginId(salesHistorySearchDTO.getSearcherName()));

        SalesHistoryStatisticsDTO salesHistoryStatisticsDTO = salesHistoryMapper.findStatisticsSearchYearByEmployee(salesHistorySearchDTO);

        if(salesHistoryStatisticsDTO == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }
        return salesHistoryStatisticsDTO;
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
    public SalesHistoryStatisticsAverageDTO selectStatisticsAverageBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {

        SalesHistoryStatisticsAverageDTO salesHistoryStatisticsAverageDTO = salesHistoryMapper.findStatisticsAverageBySearch(salesHistoryRankedDataDTO);

        if(salesHistoryStatisticsAverageDTO == null){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return salesHistoryStatisticsAverageDTO;
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

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistoryRankedDataDTO> selectStatisticsBySearchMonth(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsBySearchMonth(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsBySearchCountMonth(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistoryRankedDataDTO> selectStatisticsBySearchYear(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsBySearchYear(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsBySearchCountYear(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistoryRankedDataDTO> selectStatisticsCenterBySearch(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsCenterBySearch(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsCenterBySearchCount(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    public Page<SalesHistoryRankedDataDTO> selectStatisticsCenterBySearchMonth(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsCenterBySearchMonth(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsCenterBySearchCountMonth(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
    public Page<SalesHistoryRankedDataDTO> selectStatisticsCenterBySearchYear(SalesHistoryRankedDataDTO salesHistoryRankedDataDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<SalesHistoryRankedDataDTO> salesHistoryList = salesHistoryMapper.findStatisticsCenterBySearchYear(size,offset, salesHistoryRankedDataDTO);

        int total = salesHistoryMapper.findStatisticsCenterBySearchCountYear(salesHistoryRankedDataDTO);

        if(salesHistoryList.isEmpty() || total == 0){
            throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
        }

        return new PageImpl<>(salesHistoryList, pageable, total);
    }

    @Override
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
}
