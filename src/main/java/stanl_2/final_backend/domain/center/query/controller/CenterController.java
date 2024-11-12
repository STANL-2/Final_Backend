package stanl_2.final_backend.domain.center.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.center.common.response.ResponseMessage;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.service.CenterService;

import java.util.List;
import java.util.Map;

@RestController("queryCenterController")
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    /* 설명. 조회, 상세조회, 검색 */
    @GetMapping("")
    public ResponseEntity<?> getCenterAll(@PageableDefault(size = 20) Pageable pageable){
//        List<CenterSelectAllDTO> centerSelectAllDTOList = centerService.selectAll();

        Page<Map<String, Object>> responseCenters = centerService.selectAll(pageable);

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

}
