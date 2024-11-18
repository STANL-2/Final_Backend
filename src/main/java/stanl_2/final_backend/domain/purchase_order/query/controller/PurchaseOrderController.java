package stanl_2.final_backend.domain.purchase_order.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.purchase_order.common.response.PurchaseOrderResponseMessage;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;

import java.security.Principal;

@RestController("PurchaseOrderQueryController")
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderQueryService purchaseOrderQueryService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderQueryService purchaseOrderQueryService) {
        this.purchaseOrderQueryService = purchaseOrderQueryService;
    }

    @Operation(summary = "발주서 상세조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 상세조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<PurchaseOrderResponseMessage> getDetailPurchaseOrder(@PathVariable("id") String id,
                                                                               Authentication authentication) {

        PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO = new PurchaseOrderSelectIdDTO();
        purchaseOrderSelectIdDTO.setPurchaseOrderId(id);
        purchaseOrderSelectIdDTO.setMemberId(authentication.getName());
        purchaseOrderSelectIdDTO.setRoles(authentication.getAuthorities());

        PurchaseOrderSelectIdDTO responsePurchaseOrder = purchaseOrderQueryService.selectDetailPurchaseOrder(purchaseOrderSelectIdDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 상세 조회 성공")
                .result(responsePurchaseOrder)
                .build());
    }

    @Operation(summary = "발주서 전체조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 전체조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<PurchaseOrderResponseMessage> getAllPurchaseOrders(Authentication authentication,
                                                                             @PageableDefault(size = 10) Pageable pageable) {
        PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO = new PurchaseOrderSelectAllDTO();
        purchaseOrderSelectAllDTO.setRoles(authentication.getAuthorities());

        Page<PurchaseOrderSelectAllDTO> responsePurchaseOrders = purchaseOrderQueryService.selectAllPurchaseOrder(pageable, purchaseOrderSelectAllDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 전체 조회 성공")
                .result(responsePurchaseOrders)
                .build());
    }
}

