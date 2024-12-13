package stanl_2.final_backend.domain.contract.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.contract.common.response.ContractResponseMessage;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.service.ContractQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;

@Slf4j
@RestController("queryContractController")
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractQueryService contractQueryService;

    public ContractController(ContractQueryService contractQueryService) {
        this.contractQueryService = contractQueryService;
    }

    // 영업사원 조회
    @Operation(summary = "계약서 전체 조회(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("employee")
    public ResponseEntity<ContractResponseMessage> getAllContractEmployee(@PageableDefault(size = 10) Pageable pageable,
                                                                          Principal principal) {

        ContractSelectAllDTO contractSelectAllDTO = new ContractSelectAllDTO();
        contractSelectAllDTO.setMemberId(principal.getName());

        Page<ContractSelectAllDTO> responseContracts = contractQueryService.selectAllContractEmployee(contractSelectAllDTO, pageable);

         return ResponseEntity.ok(ContractResponseMessage.builder()
                 .httpStatus(200)
                 .msg("계약서 전체 조회 성공")
                 .result(responseContracts)
                 .build());
    }

    @Operation(summary = "계약서 상세 조회(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("employee/{contractId}")
    public ResponseEntity<ContractResponseMessage> getDetailContractEmployee(@PathVariable String contractId,
                                                                     Principal principal) {

        ContractSeletIdDTO contractSeletIdDTO = new ContractSeletIdDTO();
        contractSeletIdDTO.setContractId(contractId);
        contractSeletIdDTO.setMemberId(principal.getName());

        ContractSeletIdDTO responseContract = contractQueryService.selectDetailContractEmployee(contractSeletIdDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 상세조회 성공")
                .result(responseContract)
                .build());
    }

    @Operation(summary = "계약서 검색 조회(영업사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("employee/search")
    public ResponseEntity<ContractResponseMessage> getContractBySearchEmployee(Principal principal,
                                                                       @RequestParam(required = false) String searchMemberId,
                                                                       @RequestParam(required = false) String centerId,
                                                                       @RequestParam(required = false) String title,
                                                                       @RequestParam(required = false) String startDate,
                                                                       @RequestParam(required = false) String endDate,
                                                                       @RequestParam(required = false) String customerName,
                                                                       @RequestParam(required = false) String customerClassifcation,
                                                                       @RequestParam(required = false) String productName,
                                                                       @RequestParam(required = false) String status,
                                                                       @RequestParam(required = false) String companyName,
                                                                       @RequestParam(required = false) String customerPurchaseCondition,
                                                                               @RequestParam(required = false) String sortField,
                                                                               @RequestParam(required = false) String sortOrder,
                                                               @PageableDefault(size = 10) Pageable pageable) {

        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setMemberId(principal.getName());
        contractSearchDTO.setSearchMemberId(searchMemberId);
        contractSearchDTO.setCenterId(centerId);
        contractSearchDTO.setTitle(title);
        contractSearchDTO.setStartDate(startDate);
        contractSearchDTO.setEndDate(endDate);
        contractSearchDTO.setCustomerName(customerName);
        contractSearchDTO.setCustomerClassifcation(customerClassifcation);
        contractSearchDTO.setProductName(productName);
        contractSearchDTO.setStatus(status);
        contractSearchDTO.setCompanyName(companyName);
        contractSearchDTO.setCustomerPurchaseCondition(customerPurchaseCondition);

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<ContractSearchDTO> responseContracts = contractQueryService.selectBySearchEmployee(contractSearchDTO, pageable);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 검색 조회 성공")
                .result(responseContracts)
                .build());
    }

    // 영업 관리자 조회
    @Operation(summary = "계약서 전체 조회(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("center")
    public ResponseEntity<ContractResponseMessage> getAllContractAdmin(@PageableDefault(size = 10) Pageable pageable,
                                                                       Principal principal) throws GeneralSecurityException {

        ContractSelectAllDTO contractSelectAllDTO = new ContractSelectAllDTO();
        contractSelectAllDTO.setMemberId(principal.getName());

        Page<ContractSelectAllDTO> responseContracts = contractQueryService.selectAllContractAdmin(contractSelectAllDTO, pageable);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 전체 조회 성공")
                .result(responseContracts)
                .build());
    }

    @Operation(summary = "계약서 상세 조회(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("center/{contractId}")
    public ResponseEntity<ContractResponseMessage> getDetailContractAdmin(@PathVariable String contractId,
                                                                             Principal principal) throws GeneralSecurityException {

        ContractSeletIdDTO contractSeletIdDTO = new ContractSeletIdDTO();
        contractSeletIdDTO.setContractId(contractId);
        contractSeletIdDTO.setMemberId(principal.getName());

        ContractSeletIdDTO responseContract = contractQueryService.selectDetailContractAdmin(contractSeletIdDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 상세조회 성공")
                .result(responseContract)
                .build());
    }

    @Operation(summary = "계약서 검색 조회(영업관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("center/search")
    public ResponseEntity<ContractResponseMessage> getContractBySearchAdmin(Principal principal,
                                                                               @RequestParam(required = false) String searchMemberId,
                                                                               @RequestParam(required = false) String centerId,
                                                                               @RequestParam(required = false) String title,
                                                                               @RequestParam(required = false) String startDate,
                                                                               @RequestParam(required = false) String endDate,
                                                                               @RequestParam(required = false) String customerName,
                                                                               @RequestParam(required = false) String customerClassifcation,
                                                                               @RequestParam(required = false) String productName,
                                                                               @RequestParam(required = false) String status,
                                                                               @RequestParam(required = false) String companyName,
                                                                               @RequestParam(required = false) String customerPurchaseCondition,
                                                                                @RequestParam(required = false) String sortField,
                                                                                @RequestParam(required = false) String sortOrder,
                                                                               @PageableDefault(size = 10) Pageable pageable) throws GeneralSecurityException {

        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setMemberId(principal.getName());
        contractSearchDTO.setSearchMemberId(searchMemberId);
        contractSearchDTO.setCenterId(centerId);
        contractSearchDTO.setTitle(title);
        contractSearchDTO.setStartDate(startDate);
        contractSearchDTO.setEndDate(endDate);
        contractSearchDTO.setCustomerName(customerName);
        contractSearchDTO.setCustomerClassifcation(customerClassifcation);
        contractSearchDTO.setProductName(productName);
        contractSearchDTO.setStatus(status);
        contractSearchDTO.setCompanyName(companyName);
        contractSearchDTO.setCustomerPurchaseCondition(customerPurchaseCondition);

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<ContractSearchDTO> responseContracts = contractQueryService.selectBySearchAdmin(contractSearchDTO, pageable);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 검색 조회 성공")
                .result(responseContracts)
                .build());
    }

    // 영업담당자 조회
    @Operation(summary = "계약서 전체 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<ContractResponseMessage> getAllContract(@PageableDefault(size = 10) Pageable pageable,
                                                                  @RequestParam(required = false) String sortField,
                                                                  @RequestParam(required = false) String sortOrder) {

        ContractSelectAllDTO contractSelectAllDTO = new ContractSelectAllDTO();

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<ContractSelectAllDTO> responseContracts = contractQueryService.selectAllContract(contractSelectAllDTO, pageable);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 전체 조회 성공")
                .result(responseContracts)
                .build());
    }

    @Operation(summary = "계약서 상세 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("{contractId}")
    public ResponseEntity<ContractResponseMessage> getDetailContract(@PathVariable String contractId) {

        ContractSeletIdDTO contractSeletIdDTO = new ContractSeletIdDTO();
        contractSeletIdDTO.setContractId(contractId);

        ContractSeletIdDTO responseContract = contractQueryService.selectDetailContract(contractSeletIdDTO);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 상세조회 성공")
                .result(responseContract)
                .build());
    }

    @Operation(summary = "계약서 검색 조회(영업담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("search")
    public ResponseEntity<ContractResponseMessage> getContractBySearch(@RequestParam(required = false) String searchMemberId,
                                                                               @RequestParam(required = false) String centerId,
                                                                               @RequestParam(required = false) String title,
                                                                               @RequestParam(required = false) String startDate,
                                                                               @RequestParam(required = false) String endDate,
                                                                               @RequestParam(required = false) String customerName,
                                                                               @RequestParam(required = false) String customerClassifcation,
                                                                               @RequestParam(required = false) String productName,
                                                                               @RequestParam(required = false) String status,
                                                                               @RequestParam(required = false) String companyName,
                                                                               @RequestParam(required = false) String customerPurchaseCondition,
                                                                               @RequestParam(required = false) String sortField,
                                                                               @RequestParam(required = false) String sortOrder,
                                                                               @PageableDefault(size = 10) Pageable pageable) {

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
        contractSearchDTO.setSearchMemberId(searchMemberId);
        contractSearchDTO.setCenterId(centerId);
        contractSearchDTO.setTitle(title);
        contractSearchDTO.setStartDate(startDate);
        contractSearchDTO.setEndDate(endDate);
        contractSearchDTO.setCustomerName(customerName);
        contractSearchDTO.setCustomerClassifcation(customerClassifcation);
        contractSearchDTO.setProductName(productName);
        contractSearchDTO.setStatus(status);
        contractSearchDTO.setCompanyName(companyName);
        contractSearchDTO.setCustomerPurchaseCondition(customerPurchaseCondition);

        Page<ContractSearchDTO> responseContracts = contractQueryService.selectBySearch(contractSearchDTO, pageable);

        return ResponseEntity.ok(ContractResponseMessage.builder()
                .httpStatus(200)
                .msg("계약서 검색 조회 성공")
                .result(responseContracts)
                .build());
    }

    @Operation(summary = "엑셀 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계약서 검색 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ContractResponseMessage.class))})
    })
    @GetMapping("excel")
    public void exportContract(HttpServletResponse response) {

        contractQueryService.exportContractToExcel(response);
    }
}
