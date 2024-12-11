package stanl_2.final_backend.domain.order.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.order.command.application.dto.OrderModifyDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderStatusModifyDTO;
import stanl_2.final_backend.domain.order.command.application.service.OrderCommandService;
import stanl_2.final_backend.domain.order.common.response.OrderResponseMessage;

import java.security.GeneralSecurityException;
import java.security.Principal;

@RestController("OrderCommandController")
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderCommandService orderCommandService;

    @Autowired
    public OrderController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @Operation(summary = "수주서 등록(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 등록 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<OrderResponseMessage> postOrder(@RequestBody OrderRegistDTO orderRegistDTO,
                                                          Principal principal) throws GeneralSecurityException {

        orderRegistDTO.setMemberId(principal.getName());
        orderCommandService.registerOrder(orderRegistDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("수주서가 성공적으로 등록되었습니다.")
                                                    .result(null)
                                                    .build());
    }

    @Operation(summary = "수주서 수정(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 수정 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @PutMapping("{orderId}")
    public ResponseEntity<OrderResponseMessage> putOrder(@PathVariable String orderId,
                                                         @RequestBody OrderModifyDTO orderModifyDTO,
                                                         Principal principal) throws GeneralSecurityException {

        orderModifyDTO.setOrderId(orderId);
        orderModifyDTO.setMemberId(principal.getName());
        OrderModifyDTO orderModifyResponseDTO = orderCommandService.modifyOrder(orderModifyDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("수주서가 성공적으로 수정되었습니다.")
                                                    .result(orderModifyResponseDTO)
                                                    .build());
    }

    @Operation(summary = "수주서 삭제(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @DeleteMapping("{orderId}")
    public ResponseEntity<OrderResponseMessage> deleteTest(@PathVariable("orderId") String orderId,
                                                           Principal principal) {

        String loginId = principal.getName();
        orderCommandService.deleteOrder(orderId, loginId);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("수주서를 성공적으로 삭제하였습니다.")
                                                    .result(null)
                                                    .build());
    }

    @Operation(summary = "수주서 승인상태 변경(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 승인상태 변경 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @PutMapping("status/{orderId}")
    public ResponseEntity<OrderResponseMessage> putOrderStatus (@PathVariable String orderId,
                                                                @RequestBody OrderStatusModifyDTO orderStatusModifyDTO,
                                                                Principal principal) throws GeneralSecurityException {

        String adminLoginId = principal.getName();
        orderStatusModifyDTO.setOrderId(orderId);
        orderStatusModifyDTO.setAdminId(adminLoginId);

        orderCommandService.modifyOrderStatus(orderStatusModifyDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 승인 상태가 성공적으로 변경되었습니다.")
                .result(null)
                .build());
    }
}
