package stanl_2.final_backend.domain.A_sample.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PostRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PutRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.PutResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleService;
import stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage;

@RestController("commandSampleController")
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;


    @PostMapping("")
    public ResponseEntity<ResponseMessage> postTest(@RequestBody PostRequestDTO postRequestDTO) {

        sampleService.register(postRequestDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }

    @PutMapping("")
    public ResponseEntity<ResponseMessage> putTest(@RequestParam("mem_id") String id
            , @RequestBody PutRequestDTO putRequestDTO) {

        PutResponseDTO putResponseDTO = sampleService.modify(id, putRequestDTO);

        return ResponseEntity.ok(new ResponseMessage(200, "성공", putResponseDTO));


    }


}
