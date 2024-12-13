package stanl_2.final_backend.domain.customer.query.controller;

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
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.customer.query.dto.CustomerContractDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerSearchDTO;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;

import java.security.GeneralSecurityException;

@Slf4j
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
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
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
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/list")
    public ResponseEntity<CustomerResponseMessage> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws GeneralSecurityException {
        Pageable pageable = PageRequest.of(page, size);

        Page<CustomerDTO> customerDTOPage = customerQueryService.selectCustomerList(pageable);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(customerDTOPage)
                .build());
    }


    @Operation(summary = "고객정보 조건별 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search")
    public ResponseEntity<CustomerResponseMessage> searchCustomer(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @PageableDefault(size = 10) Pageable pageable
    ) throws GeneralSecurityException {

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO(customerId , name, sex, phone, memberId);
        Page<CustomerSearchDTO> customerDTOPage = customerQueryService.findCustomerByCondition(pageable, customerSearchDTO);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(customerDTOPage)
                .build());
    }


    @Operation(summary = "고객별 계약서 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/contract/{customerId}")
    public ResponseEntity<CustomerResponseMessage> searchCustomer(@PathVariable String customerId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<CustomerContractDTO> customerContractDTOList = customerQueryService.selectCustomerContractInfo(customerId, pageable);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(customerContractDTOList)
                .build());
    }


    @Operation(summary = "엑셀 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("excel")
    public void exportCustomer(HttpServletResponse response){

        customerQueryService.exportCustomerToExcel(response);
    }
}