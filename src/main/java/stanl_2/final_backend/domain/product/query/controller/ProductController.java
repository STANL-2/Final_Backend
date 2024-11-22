package stanl_2.final_backend.domain.product.query.controller;

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
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.center.common.response.CenterResponseMessage;
import stanl_2.final_backend.domain.product.common.response.ProductResponseMessage;
import stanl_2.final_backend.domain.product.query.dto.ProductSearchRequestDTO;
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
    public ResponseEntity<ProductResponseMessage> getProductAll(@PageableDefault(size = 20) Pageable pageable){

        Page<Map<String, Object>> responseProducts = productQueryService.selectAll(pageable);

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
    @GetMapping("{id}")
    public ResponseEntity<ProductResponseMessage> getProductById(@PathVariable("id") String id){

        ProductSelectIdDTO productSelectIdDTO  = productQueryService.selectByProductId(id);

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
            ,@PageableDefault(size = 20) Pageable pageable){

        ProductSearchRequestDTO productSearchRequestDTO = new ProductSearchRequestDTO();
        productSearchRequestDTO.setId(params.get("id"));
        productSearchRequestDTO.setName(params.get("name"));
        productSearchRequestDTO.setSerialNumber(params.get("serialNumber"));

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productSearchRequestDTO", productSearchRequestDTO);
        paramMap.put("pageable", pageable);

        Page<Map<String, Object>> responseProducts = productQueryService.selectProductBySearch(paramMap);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("제품 검색 성공")
                .result(responseProducts)
                .build());
    }
}
