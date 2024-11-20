package stanl_2.final_backend.domain.center.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.center.common.response.ResponseMessage;
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

    @GetMapping("")
    public ResponseEntity<?> getCenterAll(@PageableDefault(size = 20) Pageable pageable){

        Page<CenterSelectAllDTO> responseCenters = centerService.selectAll(pageable);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("조회 성공")
                .result(responseCenters)
                .build());
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getCenterById(@PathVariable("id") String id){

        CenterSelectIdDTO centerSelectIdDTO = centerService.selectByCenterId(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("상세 조회 성공")
                .result(centerSelectIdDTO)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCenterBySearch(@RequestParam Map<String, String> params
                                               ,@PageableDefault(size = 20) Pageable pageable){

        CenterSearchRequestDTO centerSearchRequestDTO = new CenterSearchRequestDTO();
        centerSearchRequestDTO.setId(params.get("id"));
        centerSearchRequestDTO.setName(params.get("name"));
        centerSearchRequestDTO.setAddress(params.get("address"));

        Page<CenterSelectAllDTO> responseCenters = centerService.selectBySearch(centerSearchRequestDTO, pageable);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("검색 조회 성공")
                .result(responseCenters)
                .build());
    }

}
