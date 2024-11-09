package stanl_2.final_backend.domain.center.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.center.common.response.ResponseMessage;
import stanl_2.final_backend.domain.center.query.dto.response.CenterSelectResponseDTO;
import stanl_2.final_backend.domain.center.query.repository.CenterMapper;
import stanl_2.final_backend.domain.center.query.service.CenterService;

@RestController(value="queryCenterController")
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterMapper centerMapper;
    private final CenterService centerService;

    @Autowired
    public CenterController(CenterMapper centerMapper, @Qualifier("centerService") CenterService centerService) {
        this.centerMapper = centerMapper;
        this.centerService = centerService;
    }

    @GetMapping("")
    public ResponseEntity<?> getTest(){

        CenterSelectResponseDTO centerSelectResponseDTO = centerService.selectAll();

        return ResponseEntity.ok(new ResponseMessage(200, "get 성공", " "));
    }
}
