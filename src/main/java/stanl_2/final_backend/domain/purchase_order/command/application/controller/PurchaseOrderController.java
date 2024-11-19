package stanl_2.final_backend.domain.purchase_order.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderStatusModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.service.PurchaseOrderCommandService;
import stanl_2.final_backend.domain.purchase_order.common.response.PurchaseOrderResponseMessage;

import java.security.Principal;

@Slf4j
@RestController("PurchaseOrderCommandController")
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderCommandService purchaseOrderCommandService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderCommandService purchaseOrderCommandService) {
        this.purchaseOrderCommandService = purchaseOrderCommandService;
    }

    @Operation(summary = "발주서 등록")
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

    @Operation(summary = "발주서 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 수정 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<PurchaseOrderResponseMessage> putPurchaseOrder(@PathVariable String id,
                                                                         @RequestBody PurchaseOrderModifyDTO purchaseOrderModifyDTO,
                                                                         Principal principal) {
        purchaseOrderModifyDTO.setPurchaseOrderId(id);
        purchaseOrderModifyDTO.setMemberId(principal.getName());
        PurchaseOrderModifyDTO purchaseOrderModifyResponse = purchaseOrderCommandService.modifyPurchaseOrder(purchaseOrderModifyDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                            .httpStatus(200)
                                                            .msg("발주서가 성공적으로 수정되었습니다.")
                                                            .result(purchaseOrderModifyResponse)
                                                            .build());
    }

    @Operation(summary = "발주서 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<PurchaseOrderResponseMessage> deletePurchaseOrder(@PathVariable String id,
                                                                            Principal principal) {

        purchaseOrderCommandService.deletePurchaseOrder(id, principal.getName());

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                           .httpStatus(200)
                                                           .msg("발주서가 성공적으로 삭제되었습니다.")
                                                            .result(null)
                                                           .build());
    }

    @Operation(summary = "발주서 승인 상태 수정(담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 승인 상태 수정 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @PutMapping("/stauts/{id}")
    public ResponseEntity<PurchaseOrderResponseMessage> putPurchaseOrderStatus(@PathVariable String id,
                                                                               PurchaseOrderStatusModifyDTO purchaseOrderStatusModifyDTO,
                                                                               Authentication authentication) {

        purchaseOrderStatusModifyDTO.setPurchaseOrderId(id);
        purchaseOrderStatusModifyDTO.setRoles(authentication.getAuthorities());
        purchaseOrderStatusModifyDTO.setAdminId(authentication.getName());

        purchaseOrderCommandService.modifyPurchaseOrderStatus(purchaseOrderStatusModifyDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                            .httpStatus(200)
                                                            .msg("발주서 승인 상태가 성공적으로 변경되었습니다.")
                                                            .result(null)
                                                            .build());
    }

}
