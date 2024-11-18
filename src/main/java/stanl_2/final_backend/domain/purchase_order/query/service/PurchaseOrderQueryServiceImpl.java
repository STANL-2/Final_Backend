package stanl_2.final_backend.domain.purchase_order.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderCommonException;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;
import stanl_2.final_backend.domain.purchase_order.query.repository.PurchaseOrderMapper;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final AESUtils aesUtils;

    @Autowired
    public PurchaseOrderQueryServiceImpl(PurchaseOrderMapper purchaseOrderMapper, AESUtils aesUtils) {
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.aesUtils = aesUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderSelectIdDTO selectDetailPurchaseOrder(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO) {
        if (purchaseOrderSelectIdDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()) || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))) {

            PurchaseOrderSelectIdDTO purchaseOrder = purchaseOrderMapper.findPurchaseOrderById(purchaseOrderSelectIdDTO.getPurchaseOrderId());

            if (purchaseOrder == null) {
                throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
            }
            return purchaseOrder;
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderSelectAllDTO> selectAllPurchaseOrder(Pageable pageable, PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        if (purchaseOrderSelectAllDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()) || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))) {

            List<PurchaseOrderSelectAllDTO> purchaseOrders = purchaseOrderMapper.findAllPurchaseOrder(offset, pageSize);

            if (purchaseOrders == null || purchaseOrders.isEmpty()) {
                throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
            }

            purchaseOrders.forEach(purchaseOrder -> {
                try {
                    purchaseOrder.setMemberName(aesUtils.decrypt(purchaseOrder.getMemberName()));
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
            });

            int total = purchaseOrderMapper.findAllPurchaseOrderCount();

            return new PageImpl<>(purchaseOrders, pageable, total);
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    public Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrder(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) {

        if (purchaseOrderSelectSearchDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()) || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))) {

            Map<String, Object> map = new HashMap<>();
            map.put("title", purchaseOrderSelectSearchDTO.getTitle());
            map.put("status", purchaseOrderSelectSearchDTO.getStatus());
            map.put("adminId", purchaseOrderSelectSearchDTO.getAdminId());
            map.put("searchMemberId", purchaseOrderSelectSearchDTO.getSearchMemberId());
            map.put("startDate", purchaseOrderSelectSearchDTO.getStartDate());
            map.put("endDate", purchaseOrderSelectSearchDTO.getEndDate());
            map.put("roles", purchaseOrderSelectSearchDTO.getRoles());
            map.put("pageSize", pageable.getPageSize());
            map.put("offset", pageable.getOffset());

            List<PurchaseOrderSelectSearchDTO> purchaseOrders = purchaseOrderMapper.findSearchPurchaseOrder(map);

            if (purchaseOrders == null || purchaseOrders.isEmpty()) {
                throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
            }

            purchaseOrders.forEach(purchaseOrder -> {
                try {
                    purchaseOrder.setMemberName(aesUtils.decrypt(purchaseOrder.getMemberName()));
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
            });

            int total = purchaseOrderMapper.findSearchPurchaseOrderCount();

            return new PageImpl<>(purchaseOrders, pageable, total);
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }
}