package stanl_2.final_backend.domain.customer.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.service.CustomerCommandService;
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.security.Principal;

@Slf4j
@RestController("commandCustomerController")
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerCommandService customerCommandService;
    private final AuthQueryService authQueryService;

    @Autowired
    public CustomerController(CustomerCommandService customerCommandService,
                              AuthQueryService authQueryService) {
        this.customerCommandService = customerCommandService;
        this.authQueryService = authQueryService;
    }

    @Operation(summary = "고객정보 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<CustomerResponseMessage> postCustomer(@RequestBody CustomerRegistDTO customerRegistDTO,
                                                                Principal principal) {

        String memberId = authQueryService.selectMemberIdByLoginId(principal.getName());

        customerRegistDTO.setMemberId(memberId);

        customerCommandService.registerCustomerInfo(customerRegistDTO);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(null)
                                                        .build());
    }

    @Operation(summary = "고객정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseMessage> postCustomer(@PathVariable String customerId,
                                                                @RequestBody CustomerModifyDTO customerModifyDTO) {

        customerModifyDTO.setCustomerId(customerId);

        customerCommandService.modifyCustomerInfo(customerModifyDTO);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(null)
                                                        .build());
    }

    @Operation(summary = "고객정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerResponseMessage> deleteCustomer(@PathVariable String customerId) {

        customerCommandService.deleteCustomerId(customerId);

        return ResponseEntity.ok(CustomerResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(null)
                                                        .build());
    }
}
