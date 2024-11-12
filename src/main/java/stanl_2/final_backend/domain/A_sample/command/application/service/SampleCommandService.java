package stanl_2.final_backend.domain.A_sample.command.application.service;

import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleRegistDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;

public interface SampleCommandService {
    void registerSample(SampleRegistDTO sampleRegistRequestDTO);

    SampleModifyDTO modifySample(String id, SampleModifyDTO sampleModifyDTO);

    void deleteSample(String id);
}
