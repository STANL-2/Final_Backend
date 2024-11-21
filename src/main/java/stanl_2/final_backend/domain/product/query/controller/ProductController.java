package stanl_2.final_backend.domain.product.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("")
    public ResponseEntity<ProductResponseMessage> getProductAll(@PageableDefault(size = 20) Pageable pageable){

        Page<Map<String, Object>> responseProducts = productQueryService.selectAll(pageable);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("조회 성공")
                .result(responseProducts)
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseMessage> getProductById(@PathVariable("id") String id){

        ProductSelectIdDTO productSelectIdDTO  = productQueryService.selectByProductId(id);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("상세 조회 성공")
                .result(productSelectIdDTO)
                .build());
    }

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
                .msg("검색 조회 성공")
                .result(responseProducts)
                .build());
    }
}
