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
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;
import stanl_2.final_backend.domain.schedule.common.response.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

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
    @Operation(summary = "계약서 전체 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 전채 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("{memId}")
    public ResponseEntity<ResponseMessage> getAllContract(@PathVariable("memId") String memId,
                                                          @PageableDefault(size = 10) Pageable pageable) {
        Page<Map<String, Object>> responseContracts = contractQueryService.selectAll(memId, pageable);

         return ResponseEntity.ok(ResponseMessage.builder()
                 .httpStatus(200)
                 .msg("계약서 전체 조회 성공")
                 .result(responseContracts)
                 .build());
    }

    /**
     * [GET] http://localhost:8080/api/v1/contract/CON_000000001/MEM_000000001
     * */
    @Operation(summary = "계약서 상세 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("{id}/{memId}")
    public ResponseEntity<ResponseMessage> getDetailContract(@PathVariable("id") String id,
                                                                @PathVariable("memId") String memId) {

        ContractSeletIdDTO contractDTO = new ContractSeletIdDTO();
        contractDTO.setId(id);
        contractDTO.setMemId(memId);

        ContractSeletIdDTO responseContract = contractQueryService.selectDetailContract(contractDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 상세조회 성공")
                .result(responseContract)
                .build());
    }

    /** 수정예정
     * [GET] http://localhost:8080/api/v1/contract/search?memId=MEM_000000001
     * */
    @Operation(summary = "계약서 검색 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseMessage> getContractBySearch(@RequestParam Map<String, String> params,
                                                               @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memId", params.get("memId"));
        paramMap.put("centId", params.get("centId"));
        paramMap.put("name", params.get("name"));
        paramMap.put("startAt", params.get("startAt"));
        paramMap.put("endAt", params.get("endAt"));
        paramMap.put("custName", params.get("custName"));
        paramMap.put("custCla", params.get("custCla"));
        paramMap.put("prodId", params.get("prodId"));
        paramMap.put("status", params.get("status"));
        paramMap.put("compName", params.get("compName"));
        paramMap.put("custPurCond", params.get("custCond"));
        paramMap.put("pageable", pageable);

        Page<Map<String, Object>> responseContracts = contractQueryService.selectBySearch(paramMap);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 검색 조회 성공")
                .result(responseContracts)
                .build());
    }


}
