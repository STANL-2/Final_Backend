package stanl_2.final_backend.domain.A_sample.command.application.service;

import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleRegistDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;

public interface SampleCommandService {
    void registerSample(SampleRegistDTO sampleRegistRequestDTO);

    SampleModifyDTO modifySample(String id, SampleModifyDTO sampleModifyDTO);

    void deleteSample(String id);

    void registerSampleFile(SampleRegistDTO sampleRegistRequestDTO, MultipartFile imageUrl);
}
