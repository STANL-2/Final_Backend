package stanl_2.final_backend.domain.contract.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractDeleteDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractStatusModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.service.ContractCommandService;
import stanl_2.final_backend.domain.contract.common.response.ContractResponseMessage;

import java.security.GeneralSecurityException;
import java.security.Principal;
@Slf4j
@RestController("commandContractController")
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractCommandService contractCommandService;

    @Autowired
    public ContractController(ContractCommandService contractCommandService) {
        this.contractCommandService = contractCommandService;
    }

    @Operation(summary = "계약서 등록(영업사원, 관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 등록 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ContractResponseMessage> postContract(@RequestBody ContractRegistDTO contractRegistRequestDTO,
                                                            Principal principal) throws GeneralSecurityException {

        contractRegistRequestDTO.setMemberId(principal.getName());
        contractCommandService.registerContract(contractRegistRequestDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("계약서가 성공적으로 등록되었습니다.")
                                                        .result(null)
                                                        .build());
    }

    @Operation(summary = "계약서 수정(영업사원, 관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @PutMapping("{contractId}")
    public ResponseEntity<ContractResponseMessage> putContract(@PathVariable String contractId,
                                                               Principal principal,
                                                               @RequestBody ContractModifyDTO contractModifyRequestDTO) throws GeneralSecurityException {

        contractModifyRequestDTO.setContractId(contractId);
        contractModifyRequestDTO.setMemberId(principal.getName());
        contractCommandService.modifyContract(contractModifyRequestDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서가 성공적으로 수정되었습니다.")
                .result(null)
                .build());
    }

    @Operation(summary = "계약서 삭제(영업사원, 관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @DeleteMapping("{contractId}")
    public ResponseEntity<ContractResponseMessage> deleteContract(@PathVariable String contractId) {

        ContractDeleteDTO contractDeleteDTO = new ContractDeleteDTO();
        contractDeleteDTO.setContractId(contractId);
        contractCommandService.deleteContract(contractDeleteDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서를 성공적으로 삭제하였습니다.")
                .result(null)
                .build());
    }

    @Operation(summary = "계약서 승인상태 수정(관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 승인상태 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @PutMapping("/status/{contractId}")
    public ResponseEntity<ContractResponseMessage> putContractStatus(@PathVariable String contractId,
                                                                    @RequestBody ContractStatusModifyDTO contractStatusModifyDTO,
                                                                    Principal principal) throws GeneralSecurityException {

        // DTO에 설정
        contractStatusModifyDTO.setContractId(contractId);
        contractStatusModifyDTO.setAdminId(principal.getName());

        // 서비스 호출
        contractCommandService.modifyContractStatus(contractStatusModifyDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 승인 상태가 성공적으로 변경되었습니다.")
                .result(null)
                .build());
    }

}
