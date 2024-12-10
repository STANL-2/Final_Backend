package stanl_2.final_backend.domain.purchase_order.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.purchase_order.common.response.PurchaseOrderResponseMessage;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;
import stanl_2.final_backend.domain.purchase_order.query.service.PurchaseOrderQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;

@RestController("PurchaseOrderQueryController")
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderQueryService purchaseOrderQueryService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderQueryService purchaseOrderQueryService) {
        this.purchaseOrderQueryService = purchaseOrderQueryService;
    }

    // 영업관리자 조회
    @Operation(summary = "발주서 상세조회(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 상세조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("admin/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrderResponseMessage> getDetailPurchaseOrderAdmin(@PathVariable String purchaseOrderId,
                                                                               Principal principal) {

        PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO = new PurchaseOrderSelectIdDTO();
        purchaseOrderSelectIdDTO.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderSelectIdDTO.setMemberId(principal.getName());

        PurchaseOrderSelectIdDTO responsePurchaseOrder = purchaseOrderQueryService.selectDetailPurchaseOrderAdmin(purchaseOrderSelectIdDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 상세 조회 성공")
                .result(responsePurchaseOrder)
                .build());
    }

    @Operation(summary = "발주서 전체조회(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 전체조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("admin")
    public ResponseEntity<PurchaseOrderResponseMessage> getAllPurchaseOrdersAdmin(Principal principal,
                                                                             @PageableDefault(size = 10) Pageable pageable) {
        PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO = new PurchaseOrderSelectAllDTO();
        purchaseOrderSelectAllDTO.setMemberId(principal.getName());

        Page<PurchaseOrderSelectAllDTO> responsePurchaseOrders = purchaseOrderQueryService.selectAllPurchaseOrderAdmin(pageable, purchaseOrderSelectAllDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 전체 조회 성공")
                .result(responsePurchaseOrders)
                .build());
    }

    @Operation(summary = "발주서 검색조회(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 검색조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("admin/search")
    public ResponseEntity<PurchaseOrderResponseMessage> getSearchPurchaseOrdersAdmin(@RequestParam(required = false) String title,
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

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO = new PurchaseOrderSelectSearchDTO();
        purchaseOrderSelectSearchDTO.setTitle(title);
        purchaseOrderSelectSearchDTO.setStatus(status);
        purchaseOrderSelectSearchDTO.setAdminId(adminId);
        purchaseOrderSelectSearchDTO.setStartDate(startDate);
        purchaseOrderSelectSearchDTO.setEndDate(endDate);
        purchaseOrderSelectSearchDTO.setSearchMemberId(searchMemberId);
        purchaseOrderSelectSearchDTO.setMemberId(principal.getName());
        purchaseOrderSelectSearchDTO.setProductName(productName);

        Page<PurchaseOrderSelectSearchDTO> responsePurchaseOrder = purchaseOrderQueryService.selectSearchPurchaseOrderAdmin(purchaseOrderSelectSearchDTO, pageable);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                                                            .httpStatus(200)
                                                            .msg("발주서 검색 조회 성공")
                                                            .result(responsePurchaseOrder)
                                                            .build());
    }

    // 영업담당자 조회
    @Operation(summary = "발주서 상세조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 상세조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("{purchaseOrderId}")
    public ResponseEntity<PurchaseOrderResponseMessage> getDetailPurchaseOrder(@PathVariable String purchaseOrderId) {

        PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO = new PurchaseOrderSelectIdDTO();
        purchaseOrderSelectIdDTO.setPurchaseOrderId(purchaseOrderId);

        PurchaseOrderSelectIdDTO responsePurchaseOrder = purchaseOrderQueryService.selectDetailPurchaseOrder(purchaseOrderSelectIdDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 상세 조회 성공")
                .result(responsePurchaseOrder)
                .build());
    }

    @Operation(summary = "발주서 전체조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 전체조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<PurchaseOrderResponseMessage> getAllPurchaseOrders(@PageableDefault(size = 10) Pageable pageable) {
        PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO = new PurchaseOrderSelectAllDTO();

        Page<PurchaseOrderSelectAllDTO> responsePurchaseOrders = purchaseOrderQueryService.selectAllPurchaseOrder(pageable, purchaseOrderSelectAllDTO);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 전체 조회 성공")
                .result(responsePurchaseOrders)
                .build());
    }

    @Operation(summary = "발주서 검색조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주서 검색조회 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("search")
    public ResponseEntity<PurchaseOrderResponseMessage> getSearchPurchaseOrders(@RequestParam(required = false) String title,
                                                                                     @RequestParam(required = false) String status,
                                                                                     @RequestParam(required = false) String adminId,
                                                                                     @RequestParam(required = false) String searchMemberId,
                                                                                     @RequestParam(required = false) String startDate,
                                                                                     @RequestParam(required = false) String endDate,
                                                                                    @RequestParam(required = false) String sortField,
                                                                                    @RequestParam(required = false) String sortOrder,
                                                                                @RequestParam(required = false) String productName,
                                                                                     @PageableDefault(size = 10) Pageable pageable) throws GeneralSecurityException {

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO = new PurchaseOrderSelectSearchDTO();
        purchaseOrderSelectSearchDTO.setTitle(title);
        purchaseOrderSelectSearchDTO.setStatus(status);
        purchaseOrderSelectSearchDTO.setAdminId(adminId);
        purchaseOrderSelectSearchDTO.setStartDate(startDate);
        purchaseOrderSelectSearchDTO.setEndDate(endDate);
        purchaseOrderSelectSearchDTO.setSearchMemberId(searchMemberId);
        purchaseOrderSelectSearchDTO.setProductName(productName);

        Page<PurchaseOrderSelectSearchDTO> responsePurchaseOrder = purchaseOrderQueryService.selectSearchPurchaseOrder(purchaseOrderSelectSearchDTO, pageable);

        return ResponseEntity.ok(PurchaseOrderResponseMessage.builder()
                .httpStatus(200)
                .msg("발주서 검색 조회 성공")
                .result(responsePurchaseOrder)
                .build());
    }

    @Operation(summary = "엑셀 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "엑셀 다운로드 성공",
                    content = {@Content(schema = @Schema(implementation = PurchaseOrderResponseMessage.class))})
    })
    @GetMapping("excel")
    public void exportPurchaseOrder(HttpServletResponse response) throws GeneralSecurityException {

        purchaseOrderQueryService.exportPurchaseOrder(response);
    }
}

