package stanl_2.final_backend.domain.center.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.center.common.response.CenterResponseMessage;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.service.CenterQueryService;

import java.util.List;
import java.util.Map;

@RestController("queryCenterController")
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterQueryService centerQueryService;

    @Autowired
    public CenterController(CenterQueryService centerQueryService) {
        this.centerQueryService = centerQueryService;
    }

    @Operation(summary = "영업매장 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<CenterResponseMessage> getCenterAll(@PageableDefault(size = 20) Pageable pageable,
                                                              @RequestParam(required = false) String sortField,
                                                              @RequestParam(required = false) String sortOrder){

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<CenterSelectAllDTO> responseCenters = centerQueryService.selectAll(pageable);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("조회 성공")
                .result(responseCenters)
                .build());
    }

    @Operation(summary = "영업매장 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{centerId}")
    public ResponseEntity<CenterResponseMessage> getCenterById(@PathVariable("centerId") String centerId){

        CenterSelectIdDTO centerSelectIdDTO = centerQueryService.selectByCenterId(centerId);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("상세 조회 성공")
                .result(centerSelectIdDTO)
                .build());
    }

    @Operation(summary = "영업매장 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search")
    public ResponseEntity<CenterResponseMessage> getCenterBySearch(@RequestParam Map<String, String> params
                                               ,@PageableDefault(size = 20) Pageable pageable,
                                               @RequestParam(required = false) String sortField,
                                               @RequestParam(required = false) String sortOrder){

        CenterSearchRequestDTO centerSearchRequestDTO = new CenterSearchRequestDTO();
        centerSearchRequestDTO.setCenterId(params.get("centerId"));
        centerSearchRequestDTO.setName(params.get("name"));
        centerSearchRequestDTO.setAddress(params.get("address"));

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<CenterSelectAllDTO> responseCenters = centerQueryService.selectBySearch(centerSearchRequestDTO, pageable);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("영업매장 검색 성공")
                .result(responseCenters)
                .build());
    }

    @Operation(summary = "영업매장리스트 검색(통계용)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/searchList")
    public ResponseEntity<CenterResponseMessage> getCenterListBySearch(@RequestParam Map<String, String> params){

        CenterSearchRequestDTO centerSearchRequestDTO = new CenterSearchRequestDTO();
        centerSearchRequestDTO.setCenterId(params.get("centerId"));
        centerSearchRequestDTO.setName(params.get("name"));
        centerSearchRequestDTO.setAddress(params.get("address"));

        List<CenterSelectAllDTO> responseCenters = centerQueryService.selectCenterListBySearch(centerSearchRequestDTO);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("영업매장리스트 검색(통계용) 성공")
                .result(responseCenters)
                .build());
    }

    @Operation(summary = "매장 엑셀 다운")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 엑셀 다운 테스트 성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/excel")
    public void exportCenter(HttpServletResponse response){

        centerQueryService.exportCenterToExcel(response);
    }

}
