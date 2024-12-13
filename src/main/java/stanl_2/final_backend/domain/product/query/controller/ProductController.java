package stanl_2.final_backend.domain.product.query.controller;

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
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.center.common.response.CenterResponseMessage;
import stanl_2.final_backend.domain.product.common.response.ProductResponseMessage;
import stanl_2.final_backend.domain.product.query.dto.ProductSearchRequestDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectAllDTO;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.service.ProductQueryService;

import java.util.HashMap;
import java.util.Map;

@RestController("queryProductController")
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductQueryService productQueryService;

    @Autowired
    public ProductController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @Operation(summary = "제품 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProductResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<ProductResponseMessage> getProductAll(@PageableDefault(size = 10) Pageable pageable,
                                                                @RequestParam(required = false) String sortField,
                                                                @RequestParam(required = false) String sortOrder){

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<ProductSelectAllDTO> responseProducts = productQueryService.selectAll(pageable);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("제품 조회 성공")
                .result(responseProducts)
                .build());
    }
    @Operation(summary = "제품 상세조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProductResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{productId}")
    public ResponseEntity<ProductResponseMessage> getProductById(@PathVariable("productId") String productId){

        ProductSelectIdDTO productSelectIdDTO  = productQueryService.selectByProductId(productId);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("제품 상세조회 성공")
                .result(productSelectIdDTO)
                .build());
    }
    @Operation(summary = "제품 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProductResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search")
    public ResponseEntity<ProductResponseMessage> getProductBySearch(@RequestParam Map<String, String> params
                                                                    ,@PageableDefault(size = 10) Pageable pageable){

        ProductSearchRequestDTO productSearchRequestDTO = new ProductSearchRequestDTO();
        productSearchRequestDTO.setProductId(params.get("productId"));
        productSearchRequestDTO.setName(params.get("name"));
        productSearchRequestDTO.setSerialNumber(params.get("serialNumber"));

        String sortField = params.get("sortField");
        String sortOrder = params.get("sortOrder");

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<ProductSelectAllDTO> responseProducts = productQueryService.selectProductBySearch(productSearchRequestDTO, pageable);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("제품 검색 성공")
                .result(responseProducts)
                .build());
    }

    @Operation(summary = "제품 엑셀 다운")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 엑셀 다운 성공",
                    content = {@Content(schema = @Schema(implementation = ProductResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/excel")
    public void exportProduct(HttpServletResponse response){

        productQueryService.exportProductsToExcel(response);
    }
}
