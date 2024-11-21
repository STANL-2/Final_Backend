package stanl_2.final_backend.domain.center.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.center.common.response.CenterResponseMessage;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.service.CenterService;

import java.util.Map;

@RestController("queryCenterController")
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @Operation(summary = "영업매장 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<CenterResponseMessage> getCenterAll(@PageableDefault(size = 20) Pageable pageable){

        Page<CenterSelectAllDTO> responseCenters = centerService.selectAll(pageable);

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
    @GetMapping("{id}")
    public ResponseEntity<CenterResponseMessage> getCenterById(@PathVariable("id") String id){

        CenterSelectIdDTO centerSelectIdDTO = centerService.selectByCenterId(id);

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
                                               ,@PageableDefault(size = 20) Pageable pageable){

        CenterSearchRequestDTO centerSearchRequestDTO = new CenterSearchRequestDTO();
        centerSearchRequestDTO.setId(params.get("id"));
        centerSearchRequestDTO.setName(params.get("name"));
        centerSearchRequestDTO.setAddress(params.get("address"));

        Page<CenterSelectAllDTO> responseCenters = centerService.selectBySearch(centerSearchRequestDTO, pageable);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("검색 조회 성공")
                .result(responseCenters)
                .build());
    }

}
