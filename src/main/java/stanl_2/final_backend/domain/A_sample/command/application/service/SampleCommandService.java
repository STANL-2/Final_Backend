package stanl_2.final_backend.domain.A_sample.command.application.service;

import stanl_2.final_backend.domain.A_sample.command.application.dto.request.SampleRegistRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.SampleModifyRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.SampleModifyResponseDTO;

public interface SampleCommandService {
    void registerSample(SampleRegistRequestDTO sampleRegistRequestDTO);

    SampleModifyResponseDTO modifySample(String id, SampleModifyRequestDTO sampleModifyRequestDTO);

    void deleteSample(String id);
}
