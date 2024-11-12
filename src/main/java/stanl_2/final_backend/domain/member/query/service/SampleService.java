package stanl_2.final_backend.domain.member.query.service;

import stanl_2.final_backend.domain.member.query.dto.SampleDTO;

public interface SampleService {
    String selectSampleName(String id);

    SampleDTO selectSampleInfo(String id);
}
