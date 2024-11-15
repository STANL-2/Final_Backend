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
import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.service.ContractCommandService;
import stanl_2.final_backend.domain.contract.common.response.ContractResponseMessage;

@RestController("contractController")
@RequestMapping("/api/v1/contract")
public class ContractCommandController {

    private final ContractCommandService contractCommandService;

    @Autowired
    public ContractCommandController(ContractCommandService contractCommandService) {
        this.contractCommandService = contractCommandService;
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
                                                          @RequestBody ContractRegistDTO contractRegistRequestDTO) {
        contractRegistRequestDTO.setMemId(id);
        contractCommandService.registerContract(contractRegistRequestDTO);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }

    /**
     * [PUT] http://localhost:8080/api/v1/contract/CON_000000011
     * Request
     *  {
     *   "name": "계약서 수정",
     *   "custName": "John Doe",
     *   "custIdenNo": "123456-7890123",
     *   "custAddrress": "123 Main Street, City, Country",
     *   "custEmail": "johndoe@example.com",
     *   "custPhone": "+1-234-567-8901",
     *   "compName": "Doe Industries",
     *   "custCla": "Premium",
     *   "custPurCond": "Full Payment",
     *   "seriNum": "A1B2C3D4",
     *   "seleOpti": "Extended Warranty",
     *   "downPay": 10000,
     *   "intePay": 500,
     *   "remPay": 15000,
     *   "consPay": 5000,
     *   "delvDate": "2024-12-15",
     *   "delvLoc": "Warehouse No. 3, Industrial Park",
     *   "state": "WAIT",
     *   "noOfVeh": "2",
     *   "memId": "MEM_000000001"
     * }
     * */
    @Operation(summary = "계약서 수정 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<ContractResponseMessage> putContract(@PathVariable String id,
                                                                               @RequestBody ContractModifyDTO contractModifyRequestDTO) {

        contractModifyRequestDTO.setId(id);
        ContractModifyDTO contractModifyDTO = contractCommandService.modifyContract(contractModifyRequestDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서가 수정되었습니다.")
                .result(contractModifyDTO)
                .build());
    }

    /**
     * [DELETE] http://localhost:7777/api/v1/sample?mem_id=SAM_000001
     * */
    @Operation(summary = "샘플 삭제 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<ContractResponseMessage> deleteContract(@PathVariable String id) {

        // 회원 아이디 받아와야 함
        contractCommandService.deleteContract(id);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }
}
