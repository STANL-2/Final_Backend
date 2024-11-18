package stanl_2.final_backend.domain.customer.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;

import java.security.GeneralSecurityException;

@RestController(value = "queryCustomerController")
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    @Autowired
    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @Operation(summary = "고객정보 상세조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseMessage> getCustomerInfo(@PathVariable String customerId) throws GeneralSecurityException {

        CustomerDTO customerInfoDTO = customerQueryService.selectCustomerInfo(customerId);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(customerInfoDTO)
                                                        .build());
    }


    @Operation(summary = "고객번호로 전체 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/list")
    public ResponseEntity<CustomerResponseMessage> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);

        Page<CustomerDTO> customerDTOPage = customerQueryService.selectCustomerList(pageable);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(customerDTOPage)
                .build());
    }

}
