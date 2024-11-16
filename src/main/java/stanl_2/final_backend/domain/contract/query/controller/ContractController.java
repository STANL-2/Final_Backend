package stanl_2.final_backend.domain.contract.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.contract.common.response.ContractResponseMessage;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;

@Slf4j
@RestController("queryContractController")
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractQueryService contractQueryService;

    public ContractController(ContractQueryService contractQueryService) {
        this.contractQueryService = contractQueryService;
    }

    /**
     * [GET] http://localhost:8080/api/v1/contract/MEM_000000001?page=0&size=10
     * */
    @Operation(summary = "계약서 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 전채 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("{memberId}")
    public ResponseEntity<ContractResponseMessage> getAllContract(@PathVariable("memberId") String memberId,
                                                          @PageableDefault(size = 10) Pageable pageable) {

        // 회원 아이디 받아 오는건 나중에 수정할 예정

        Page<ContractSelectAllDTO> responseContracts = contractQueryService.selectAll(memberId, pageable);

         return ResponseEntity.ok(ContractResponseMessage.builder()
                 .httpStatus(200)
                 .msg("계약서 전체 조회 성공")
                 .result(responseContracts)
                 .build());
    }

    /**
     * [GET] http://localhost:8080/api/v1/contract/CON_000000001/MEM_000000001
     * */
    @Operation(summary = "계약서 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("{id}/{memberId}")
    public ResponseEntity<ContractResponseMessage> getDetailContract(@PathVariable("id") String id,
                                                                @PathVariable("memberId") String memberId) {

        ContractSeletIdDTO contractDTO = new ContractSeletIdDTO();
        contractDTO.setContractId(id);
        contractDTO.setMemberId(memberId);

        ContractSeletIdDTO responseContract = contractQueryService.selectDetailContract(contractDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 상세조회 성공")
                .result(responseContract)
                .build());
    }

    /** 수정예정
     * [GET] http://localhost:8080/api/v1/contract/search?memId=MEM_000000001
     * */
    @Operation(summary = "계약서 검색 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("/search")
    public ResponseEntity<ContractResponseMessage> getContractBySearch(@RequestParam(required = false) String memberId,
                                                                       @RequestParam(required = false) String memId,
                                                                       @RequestParam(required = false) String centerId,
                                                                       @RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String startAt,
                                                                       @RequestParam(required = false) String endAt,
                                                                       @RequestParam(required = false) String customerName,
                                                                       @RequestParam(required = false) String customerClassifcation,
                                                                       @RequestParam(required = false) String productId,
                                                                       @RequestParam(required = false) String status,
                                                                       @RequestParam(required = false) String companyName,
                                                                       @RequestParam(required = false) String customerPurchaseCondition,
                                                               @PageableDefault(size = 10) Pageable pageable) {


        ContractSearchDTO contractSearchDTO = new ContractSearchDTO(memberId, memId, centerId, name, startAt, endAt,
                customerName, customerClassifcation, productId, status, companyName, customerPurchaseCondition);
        Page<ContractSearchDTO> responseContracts = contractQueryService.selectBySearch(contractSearchDTO, pageable);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 검색 조회 성공")
                .result(responseContracts)
                .build());
    }


}
