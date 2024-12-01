package stanl_2.final_backend.domain.purchase_order.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderExcelDTO;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderCommonException;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;
import stanl_2.final_backend.domain.purchase_order.query.repository.PurchaseOrderMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.util.List;
@Service
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final AuthQueryService authQueryService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public PurchaseOrderQueryServiceImpl(PurchaseOrderMapper purchaseOrderMapper, AuthQueryService authQueryService, RedisTemplate<String, Object> redisTemplate, ExcelUtilsV1 excelUtilsV1) {
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.authQueryService = authQueryService;
        this.redisTemplate = redisTemplate;
        this.excelUtilsV1 = excelUtilsV1;
    }

    // 영업 관리자 조회
    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderSelectIdDTO selectDetailPurchaseOrderAdmin(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO) {

        String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderSelectIdDTO.getMemberId());

        PurchaseOrderSelectIdDTO purchaseOrder = purchaseOrderMapper.findPurchaseOrderByPurchaseOrderIdAndMemberId(purchaseOrderSelectIdDTO.getPurchaseOrderId(), memberId);

        if (purchaseOrder == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        String unescapedUrl = StringEscapeUtils.unescapeJson(purchaseOrder.getContent());
        purchaseOrder.setContent(unescapedUrl);

        return purchaseOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderSelectAllDTO> selectAllPurchaseOrderAdmin(Pageable pageable, PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO) {

        String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderSelectAllDTO.getMemberId());

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        String cacheKey = "myCache::purchaseOrders::offset=" + offset + "::pageSize=" + pageSize;

        // 캐시 조회
        List<PurchaseOrderSelectAllDTO> purchaseOrders = (List<PurchaseOrderSelectAllDTO>) redisTemplate.opsForValue().get(cacheKey);

        if (purchaseOrders == null) {
            purchaseOrders = purchaseOrderMapper.findAllPurchaseOrderByMemberId(offset, pageSize, memberId);

            if(purchaseOrders == null) {
                throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
            }

            redisTemplate.opsForValue().set(cacheKey, purchaseOrders);
        }

        Integer count = purchaseOrderMapper.findAllPurchaseOrderCountByMemberId(memberId);
        int totalPurchaseOrder = (count != null) ? count : 0;

        return new PageImpl<>(purchaseOrders, pageable, totalPurchaseOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrderAdmin(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) {

        String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderSelectSearchDTO.getMemberId());
        purchaseOrderSelectSearchDTO.setMemberId(memberId);

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<PurchaseOrderSelectSearchDTO> purchaseOrders = purchaseOrderMapper.findSearchPurchaseOrderMemberId(offset, pageSize, purchaseOrderSelectSearchDTO);

        if (purchaseOrders == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        Integer count = purchaseOrderMapper.findSearchPurchaseOrderCountMemberId(purchaseOrderSelectSearchDTO);
        int totalPurchaseOrder = (count != null) ? count : 0;

        return new PageImpl<>(purchaseOrders, pageable, totalPurchaseOrder);
    }

    // 영업 담장자 조회
    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderSelectIdDTO selectDetailPurchaseOrder(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO) {

        PurchaseOrderSelectIdDTO purchaseOrder = purchaseOrderMapper.findPurchaseOrderByPurchaseOrderId(purchaseOrderSelectIdDTO.getPurchaseOrderId());

        if (purchaseOrder == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }
        return purchaseOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderSelectAllDTO> selectAllPurchaseOrder(Pageable pageable, PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        String cacheKey = "myCache::purchaseOrders::offset=" + offset + "::size=" + pageSize;

        // 캐시 조회
        List<PurchaseOrderSelectAllDTO> purchaseOrders = (List<PurchaseOrderSelectAllDTO>) redisTemplate.opsForValue().get(cacheKey);

        if (purchaseOrders == null) {
            purchaseOrders = purchaseOrderMapper.findAllPurchaseOrder(offset, pageSize);

            if(purchaseOrders == null) {
                throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
            }

            redisTemplate.opsForValue().set(cacheKey, purchaseOrders);
        }

        Integer count = purchaseOrderMapper.findAllPurchaseOrderCount();
        int totalPurchaseOrder = (count != null) ? count : 0;

        return new PageImpl<>(purchaseOrders, pageable, totalPurchaseOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrder(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<PurchaseOrderSelectSearchDTO> purchaseOrders = purchaseOrderMapper.findSearchPurchaseOrder(offset, pageSize, purchaseOrderSelectSearchDTO, sortField, sortOrder);

        if (purchaseOrders == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        int count = purchaseOrderMapper.findSearchPurchaseOrderCount(purchaseOrderSelectSearchDTO);
        if (count == 0) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }
        System.out.println("dkjdkdk:");
        return new PageImpl<>(purchaseOrders, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public void exportPurchaseOrder(HttpServletResponse response) {

        List<PurchaseOrderExcelDTO> purchaseOrderExcels = purchaseOrderMapper.findPurchaseOrderForExcel();
        if(purchaseOrderExcels == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }
        excelUtilsV1.download(PurchaseOrderExcelDTO.class, purchaseOrderExcels, "purchaseOrderExcel", response);
    }
}