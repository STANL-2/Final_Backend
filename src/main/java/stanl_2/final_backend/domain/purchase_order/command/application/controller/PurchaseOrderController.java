package stanl_2.final_backend.domain.purchase_order.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderStatusModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.service.PurchaseOrderCommandService;
import stanl_2.final_backend.domain.purchase_order.common.response.PurchaseOrderResponseMessage;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;

@Slf4j
@RestController("PurchaseOrderCommandController")
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderCommandService purchaseOrderCommandService;
    private final PurchaseOrderQueryService purchaseOrderQueryService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderCommandService purchaseOrderCommandService, @Qualifier("purchaseOrderQueryService") PurchaseOrderQueryService purchaseOrderQueryService) {
        this.purchaseOrderCommandService = purchaseOrderCommandService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
    }

    @Operation(summary = "발주서 등록(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 등록 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<PurchaseOrderResponseMessage> postPurchaseOrder(@RequestBody PurchaseOrderRegistDTO purchaseOrderRegistDTO,
                                                                          Principal principal) {

        purchaseOrderRegistDTO.setMemberId(principal.getName());

        purchaseOrderCommandService.registerPurchaseOrder(purchaseOrderRegistDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                   .httpStatus(200)
                                                   .msg("발주서가 성공적으로 등록되었습니다.")
                                                   .build());
    }

    @Operation(summary = "발주서 수정(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 수정 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @PutMapping("{purchaseOrderId}")
    public ResponseEntity<PurchaseOrderResponseMessage> putPurchaseOrder(@PathVariable String purchaseOrderId,
                                                                         @RequestBody PurchaseOrderModifyDTO purchaseOrderModifyDTO,
                                                                         Principal principal) {
        purchaseOrderModifyDTO.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderModifyDTO.setMemberId(principal.getName());
        PurchaseOrderModifyDTO purchaseOrderModifyResponse = purchaseOrderCommandService.modifyPurchaseOrder(purchaseOrderModifyDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                            .httpStatus(200)
                                                            .msg("발주서가 성공적으로 수정되었습니다.")
                                                            .result(purchaseOrderModifyResponse)
                                                            .build());
    }

    @Operation(summary = "발주서 삭제(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @DeleteMapping("{purchaseOrderId}")
    public ResponseEntity<PurchaseOrderResponseMessage> deletePurchaseOrder(@PathVariable String purchaseOrderId,
                                                                            Principal principal) {

        purchaseOrderCommandService.deletePurchaseOrder(purchaseOrderId, principal.getName());

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                           .httpStatus(200)
                                                           .msg("발주서가 성공적으로 삭제되었습니다.")
                                                            .result(null)
                                                           .build());
    }

    @Operation(summary = "발주서 승인 상태 수정(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 승인 상태 수정 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @PutMapping("status/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrderResponseMessage> putPurchaseOrderStatus(@PathVariable String purchaseOrderId,
                                                                               @RequestBody PurchaseOrderStatusModifyDTO purchaseOrderStatusModifyDTO,
                                                                               Principal principal) throws GeneralSecurityException {

        purchaseOrderStatusModifyDTO.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderStatusModifyDTO.setAdminId(principal.getName());

        purchaseOrderCommandService.modifyPurchaseOrderStatus(purchaseOrderStatusModifyDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                            .httpStatus(200)
                                                            .msg("발주서 승인 상태가 성공적으로 변경되었습니다.")
                                                            .result(null)
                                                            .build());
    }
}
