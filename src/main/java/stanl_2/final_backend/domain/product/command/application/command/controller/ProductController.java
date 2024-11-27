package stanl_2.final_backend.domain.product.command.application.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.product.command.application.command.dto.ProductRegistDTO;
import stanl_2.final_backend.domain.product.command.application.command.service.ProductCommandService;
import stanl_2.final_backend.domain.product.common.response.ProductResponseMessage;

@RestController("commandProductController")
@RequestMapping("/api/v1/product")

public class ProductController {

    private final ProductCommandService productCommandService;

    @Autowired
    public ProductController(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @Operation(summary = "제품 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProductResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ProductResponseMessage> postTest(@RequestPart("dto") ProductRegistDTO productRegistDTO,
                                                           @RequestPart("file") MultipartFile imageUrl){
        productCommandService.registProduct(productRegistDTO, imageUrl);

        return ResponseEntity.ok(ProductResponseMessage.builder()
                .httpStatus(200)
                .msg("제품 등록 성공")
                .result(null)
                .build());
    }
}
