package stanl_2.final_backend.domain.contract.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.contract.query.dto.ContractDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractService;
import stanl_2.final_backend.domain.schedule.common.response.ResponseMessage;

@RestController("queryContractController")
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @Operation(summary = "계약서 상세 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("{id}/{memId}")
    public ResponseEntity<ResponseMessage> selectDetailContract(@PathVariable("id") String id,
                                                                @PathVariable("memId") String memId) {

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setId(id);
        contractDTO.setMemId(memId);

        ContractDTO responseContract = contractService.selectDetailContract(contractDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 상세조회 성공")
                .result(responseContract)
                .build());
    }
}
