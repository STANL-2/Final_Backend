package stanl_2.final_backend.domain.purchase_order.query.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderExcelDTO;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderCommonException;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;
import stanl_2.final_backend.domain.purchase_order.query.repository.PurchaseOrderMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Service("purchaseOrderQueryService")
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final AuthQueryService authQueryService;
    private final MemberQueryService memberQueryService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExcelUtilsV1 excelUtilsV1;
    private final AESUtils aesUtils;

    @Autowired
    public PurchaseOrderQueryServiceImpl(PurchaseOrderMapper purchaseOrderMapper, AuthQueryService authQueryService,
                                         RedisTemplate<String, Object> redisTemplate, ExcelUtilsV1 excelUtilsV1,
                                         MemberQueryService memberQueryService, AESUtils aesUtils) {
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.authQueryService = authQueryService;
        this.redisTemplate = redisTemplate;
        this.excelUtilsV1 = excelUtilsV1;
        this.memberQueryService = memberQueryService;
        this.aesUtils = aesUtils;
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
    public Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrderAdmin(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException {

        String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderSelectSearchDTO.getMemberId());
        purchaseOrderSelectSearchDTO.setMemberId(memberId);

        if ("대기".equals(purchaseOrderSelectSearchDTO.getStatus())) {
            purchaseOrderSelectSearchDTO.setStatus("WAIT");
        }
        if ("승인".equals(purchaseOrderSelectSearchDTO.getStatus())) {
            purchaseOrderSelectSearchDTO.setStatus("APPROVED");
        }
        if ("취소".equals(purchaseOrderSelectSearchDTO.getStatus())) {
            purchaseOrderSelectSearchDTO.setStatus("CANCEL");
        }

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

        List<PurchaseOrderSelectSearchDTO> purchaseOrders = purchaseOrderMapper.findSearchPurchaseOrderMemberId(offset, pageSize, purchaseOrderSelectSearchDTO, sortField, sortOrder);

        if (purchaseOrders == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        for (PurchaseOrderSelectSearchDTO purchaseOrder : purchaseOrders) {
            if (purchaseOrder.getMemberId() != null) {
                String memberName = memberQueryService.selectNameById(purchaseOrder.getMemberId());
                purchaseOrder.setMemberName(memberName);
            }

            if (purchaseOrder.getAdminId() != null) {
                String adminName = memberQueryService.selectNameById(purchaseOrder.getAdminId());
                purchaseOrder.setAdminName(adminName);
            } else {
                purchaseOrder.setAdminName("-");
            }
        }

        int count = purchaseOrderMapper.findSearchPurchaseOrderCountMemberId(purchaseOrderSelectSearchDTO);

        if (count == 0) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        return new PageImpl<>(purchaseOrders, pageable, count);
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
    public Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrder(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException {

        if ("대기".equals(purchaseOrderSelectSearchDTO.getStatus())) {
            purchaseOrderSelectSearchDTO.setStatus("WAIT");
        }
        if ("승인".equals(purchaseOrderSelectSearchDTO.getStatus())) {
            purchaseOrderSelectSearchDTO.setStatus("APPROVED");
        }
        if ("취소".equals(purchaseOrderSelectSearchDTO.getStatus())) {
            purchaseOrderSelectSearchDTO.setStatus("CANCEL");
        }

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

        for (PurchaseOrderSelectSearchDTO purchaseOrder : purchaseOrders) {
            if (purchaseOrder.getMemberId() != null) {
                String memberName = memberQueryService.selectNameById(purchaseOrder.getMemberId());
                purchaseOrder.setMemberName(memberName);
            }

            if (purchaseOrder.getAdminId() != null) {
                String adminName = memberQueryService.selectNameById(purchaseOrder.getAdminId());
                purchaseOrder.setAdminName(adminName);
            } else {
                purchaseOrder.setAdminName("-");
            }
        }

        int count = purchaseOrderMapper.findSearchPurchaseOrderCount(purchaseOrderSelectSearchDTO);

        if (count == 0) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        return new PageImpl<>(purchaseOrders, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public void exportPurchaseOrder(HttpServletResponse response) throws GeneralSecurityException {

        List<PurchaseOrderExcelDTO> purchaseOrderExcels = purchaseOrderMapper.findPurchaseOrderForExcel();

        if(purchaseOrderExcels == null) {
            throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
        }

        for(int i=0;i<purchaseOrderExcels.size();i++){
            purchaseOrderExcels.get(i).getMemberName();
            PurchaseOrderExcelDTO purchaseOrderExcelDTO = purchaseOrderExcels.get(i);
            purchaseOrderExcelDTO.setMemberName(aesUtils.decrypt(purchaseOrderExcelDTO.getMemberName()));
            purchaseOrderExcels.set(i, purchaseOrderExcelDTO);
        }

        excelUtilsV1.download(PurchaseOrderExcelDTO.class, purchaseOrderExcels, "purchaseOrderExcel", response);
    }
}