package stanl_2.final_backend.domain.center.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.center.common.response.ResponseMessage;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.service.CenterService;

@RestController("queryCenterController")
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping("{centerId}")
    public ResponseEntity<?> getTest(@PathVariable Long centerId){

        CenterSelectIdDTO centerSelectIdDTO = centerService.selectByCenterId(centerId);

        return ResponseEntity.ok(new ResponseMessage(200, "get 성공", centerSelectIdDTO));
    }
}
