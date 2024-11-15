package stanl_2.final_backend.domain.order.query.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.order.common.response.OrderResponseMessage;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.service.OrderQueryService;

import java.util.Map;

@RestController("queryOrderController")
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderQueryService orderQueryService;

    @Autowired
    public OrderController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @Operation(summary = "수주서 전제 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 전제 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("{memberId}")
    public ResponseEntity<OrderResponseMessage> getAllOrder(@PathVariable("memberId") String memberId,
                                                            @PageableDefault(size = 10)Pageable pageable) {

        // 회원 아이디 받아 오는건 나중에 수정할 예정

        Page<OrderSelectAllDTO> responseOrders = orderQueryService.selectAll(memberId, pageable);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                   .httpStatus(200)
                                                   .msg("수주서 전체 조회 성공")
                                                   .result(responseOrders)
                                                    .build());
    }
}
