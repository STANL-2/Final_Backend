package stanl_2.final_backend.domain.purchase_order.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderCommonException;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.repository.PurchaseOrderMapper;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

@Service
@Transactional(readOnly = true)
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final AuthQueryService authQueryService;

    @Autowired
    public PurchaseOrderQueryServiceImpl(PurchaseOrderMapper purchaseOrderMapper, AuthQueryService authQueryService) {
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.authQueryService = authQueryService;
    }

    @Override
    public PurchaseOrderSelectIdDTO selectDetailPurchaseOrder(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO) {
        if(purchaseOrderSelectIdDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getAuthority()))) {

            String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderSelectIdDTO.getMemberId());

            PurchaseOrderSelectIdDTO purchaseOrder = purchaseOrderMapper.findPurchaseOrderByIdAndMemberId(purchaseOrderSelectIdDTO.getPurchaseOrderId(), memberId);

            if (purchaseOrder == null) {
                throw new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND);
            }
            return purchaseOrder;
        } else if (purchaseOrderSelectIdDTO.getRoles().stream()
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
}