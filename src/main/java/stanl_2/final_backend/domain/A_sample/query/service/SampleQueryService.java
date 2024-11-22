package stanl_2.final_backend.domain.A_sample.query.service;

import jakarta.servlet.http.HttpServletResponse;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;

public interface SampleQueryService {
    String selectSampleName(String id);

    SampleDTO selectSampleInfo(String id);

    void exportSamplesToExcel(HttpServletResponse response);
}
