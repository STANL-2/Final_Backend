package stanl_2.final_backend.domain.A_sample.query.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;
import stanl_2.final_backend.domain.A_sample.query.repository.SampleMapper;

@Slf4j
@Service(value = "querySampleService")
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService{

    private final SampleMapper sampleMapper;

    @Override
    @Transactional(readOnly = true)
    public String findName(String id) {
        return sampleMapper.selectNameById(id);
    }

    @Override
    public SampleDTO findById(String id) {
        return sampleMapper.selectById(id);
    }


}
