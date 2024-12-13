package stanl_2.final_backend.domain.order.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.order.common.response.OrderResponseMessage;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;
import stanl_2.final_backend.domain.order.query.service.OrderQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;

@Slf4j
@RestController("queryOrderController")
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderQueryService orderQueryService;

    @Autowired
    public OrderController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @Operation(summary = "수주서 전체 조회(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("employee")
    public ResponseEntity<OrderResponseMessage> getAllOrderEmployee(Principal principal,
                                                            @PageableDefault(size = 10)Pageable pageable,
                                                               @RequestParam(required = false) String sortField,
                                                                    @RequestParam(required = false) String sortOrder) {

        String loginId = principal.getName();

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<OrderSelectAllDTO> responseOrders = orderQueryService.selectAllEmployee(loginId, pageable);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                   .httpStatus(200)
                                                   .msg("수주서 전체 조회 성공")
                                                   .result(responseOrders)
                                                    .build());
    }

    @Operation(summary = "수주서 상세 조회(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("employee/{orderId}")
    public ResponseEntity<OrderResponseMessage> getDetailOrderEmployee(@PathVariable("orderId") String orderId,
                                                               Principal principal) {

        String memberId = principal.getName();
        OrderSelectIdDTO orderSelectIdDTO = new OrderSelectIdDTO();
        orderSelectIdDTO.setOrderId(orderId);
        orderSelectIdDTO.setMemberId(memberId);

        OrderSelectIdDTO responseOrder = orderQueryService.selectDetailOrderEmployee(orderSelectIdDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                                                   .httpStatus(200)
                                                   .msg("수주서 상세 조회 성공")
                                                   .result(responseOrder)
                                                    .build());
    }

    @Operation(summary = "수주서 검색 조회(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("employee/search")
    public ResponseEntity<OrderResponseMessage> getSearchOrderEmployee(@RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String status,
                                                               @RequestParam(required = false) String adminId,
                                                               @RequestParam(required = false) String searchMemberId,
                                                               @RequestParam(required = false) String startDate,
                                                               @RequestParam(required = false) String endDate,
                                                                       @RequestParam(required = false) String productName,
                                                                       @RequestParam(required = false) String sortField,
                                                                       @RequestParam(required = false) String sortOrder,
                                                               Principal principal,
                                                               @PageableDefault(size = 10) Pageable pageable) throws GeneralSecurityException {

        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setTitle(title);
        orderSelectSearchDTO.setStatus(status);
        orderSelectSearchDTO.setAdminId(adminId);
        orderSelectSearchDTO.setSearchMemberId(searchMemberId);
        orderSelectSearchDTO.setStartDate(startDate);
        orderSelectSearchDTO.setEndDate(endDate);
        orderSelectSearchDTO.setMemberId(principal.getName());
        orderSelectSearchDTO.setProductName(productName);

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<OrderSelectSearchDTO> responseOrders = orderQueryService.selectSearchOrdersEmployee(orderSelectSearchDTO, pageable);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 검색 조회 성공")
                .result(responseOrders)
                .build());
    }

    // 영업담당자 조회
    @Operation(summary = "수주서 전체 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<OrderResponseMessage> getAllOrder(@PageableDefault(size = 10)Pageable pageable) {

        Page<OrderSelectAllDTO> responseOrders = orderQueryService.selectAll(pageable);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 전체 조회 성공")
                .result(responseOrders)
                .build());
    }

    @Operation(summary = "수주서 상세 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponseMessage> getDetailOrder(@PathVariable("orderId") String orderId) {

        OrderSelectIdDTO orderSelectIdDTO = new OrderSelectIdDTO();
        orderSelectIdDTO.setOrderId(orderId);

        OrderSelectIdDTO responseOrder = orderQueryService.selectDetailOrder(orderSelectIdDTO);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 상세 조회 성공")
                .result(responseOrder)
                .build());
    }

    @Operation(summary = "수주서 검색 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("search")
    public ResponseEntity<OrderResponseMessage> getSearchOrder(@RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String orderId,
                                                               @RequestParam(required = false) String status,
                                                               @RequestParam(required = false) String adminId,
                                                               @RequestParam(required = false) String searchMemberId,
                                                               @RequestParam(required = false) String startDate,
                                                               @RequestParam(required = false) String endDate,
                                                               @RequestParam(required = false) String sortField,
                                                               @RequestParam(required = false) String sortOrder,
                                                               @RequestParam(required = false) String productName,
                                                               @PageableDefault(size = 10) Pageable pageable) throws GeneralSecurityException {

        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setTitle(title);
        orderSelectSearchDTO.setStatus(status);
        orderSelectSearchDTO.setAdminId(adminId);
        orderSelectSearchDTO.setSearchMemberId(searchMemberId);
        orderSelectSearchDTO.setStartDate(startDate);
        orderSelectSearchDTO.setEndDate(endDate);
        orderSelectSearchDTO.setProductName(productName);
        orderSelectSearchDTO.setOrderId(orderId);

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<OrderSelectSearchDTO> responseOrders = orderQueryService.selectSearchOrders(orderSelectSearchDTO, pageable);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 검색 조회 성공")
                .result(responseOrders)
                .build());
    }

    // 영업관리자
    @Operation(summary = "수주서 전체 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("center")
    public ResponseEntity<OrderResponseMessage> getAllOrderCenter(@PageableDefault(size = 10)Pageable pageable,
                                                                  Principal principal) throws GeneralSecurityException {

        String memberId = principal.getName();
        Page<OrderSelectAllDTO> responseOrders = orderQueryService.selectAllCenter(pageable, memberId);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 전체 조회 성공")
                .result(responseOrders)
                .build());
    }

    @Operation(summary = "수주서 검색 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("center/search")
    public ResponseEntity<OrderResponseMessage> getSearchOrderCenter(
                                                               Principal principal,
                                                               @RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String orderId,
                                                               @RequestParam(required = false) String status,
                                                               @RequestParam(required = false) String adminId,
                                                               @RequestParam(required = false) String searchMemberId,
                                                               @RequestParam(required = false) String startDate,
                                                               @RequestParam(required = false) String endDate,
                                                               @RequestParam(required = false) String sortField,
                                                               @RequestParam(required = false) String sortOrder,
                                                               @RequestParam(required = false) String productName,
                                                               @PageableDefault(size = 10) Pageable pageable) throws GeneralSecurityException {

        OrderSelectSearchDTO orderSelectSearchDTO = new OrderSelectSearchDTO();
        orderSelectSearchDTO.setTitle(title);
        orderSelectSearchDTO.setStatus(status);
        orderSelectSearchDTO.setAdminId(adminId);
        orderSelectSearchDTO.setSearchMemberId(searchMemberId);
        orderSelectSearchDTO.setStartDate(startDate);
        orderSelectSearchDTO.setEndDate(endDate);
        orderSelectSearchDTO.setProductName(productName);
        orderSelectSearchDTO.setOrderId(orderId);
        orderSelectSearchDTO.setMemberId(principal.getName());

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<OrderSelectSearchDTO> responseOrders = orderQueryService.selectSearchOrdersCenter(orderSelectSearchDTO, pageable);

        return ResponseEntity.ok(OrderResponseMessage.builder()
                .httpStatus(200)
                .msg("수주서 검색 조회 성공")
                .result(responseOrders)
                .build());
    }

    @Operation(summary = "수주서 엑셀 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수주서 엑셀 다운로드 성공",
                    content = {@Content(schema = @Schema(implementation = OrderResponseMessage.class))})
    })
    @GetMapping("excel")
    public void exportOrder(HttpServletResponse response) {

        orderQueryService.exportOrder(response);
    }
}
