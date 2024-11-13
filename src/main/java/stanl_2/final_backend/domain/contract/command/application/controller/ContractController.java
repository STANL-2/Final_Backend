package stanl_2.final_backend.domain.contract.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.contract.command.application.dto.request.ContractRegistRequestDTO;
import stanl_2.final_backend.domain.contract.command.application.service.ContractService;

@RestController("contractController")
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * [POST] http://localhost:7777/api/v1/contract
     * Request
     *  {
     *     "name": "tes1",
     *     "num": 123
     *  }
     * */
    @Operation(summary = "계약서 등록 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PostMapping("{id}")
    public ResponseEntity<SampleResponseMessage> postTest(@PathVariable String id,
                                                          @RequestBody ContractRegistRequestDTO contractRegistRequestDTO) {
        contractRegistRequestDTO.setMemId(id);
        contractService.registerContract(contractRegistRequestDTO);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }
}
