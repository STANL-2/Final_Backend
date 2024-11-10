package stanl_2.final_backend.domain.A_sample.query.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage;
import stanl_2.final_backend.domain.A_sample.query.service.SampleService;

@RestController(value = "querySampleController")
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    /**
     * [GET] http://localhost:7777/api/v1/sample?id=SAM_000001
     * */
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getTest(@RequestParam("id") String id) {
        String name = sampleService.findName(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                                            .httpStatus(200)
                                            .msg("성공")
                                            .result(name)
                                            .build());
    }

}
