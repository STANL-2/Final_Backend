package stanl_2.final_backend.domain.purchase_order.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.service.PurchaseOrderCommandService;
import stanl_2.final_backend.domain.purchase_order.common.response.PurchaseOrderResponseMessage;

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
    public ResponseEntity<PurchaseOrderResponseMessage> postPurchaseOrder(@RequestBody PurchaseOrderRegistDTO purchaseOrderRegistDTO) {

        purchaseOrderCommandService.registerPurchaseOrder(purchaseOrderRegistDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                   .httpStatus(200)
                                                   .msg("발주서가 성공적으로 등록되었습니다.")
                                                   .build());
    }
}
