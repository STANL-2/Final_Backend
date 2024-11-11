package stanl_2.final_backend.domain.A_sample.query.service;

import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;

public interface SampleService {
    String selectSampleName(String id);

    SampleDTO selectSampleInfo(String id);
}
