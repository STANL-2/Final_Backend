package stanl_2.final_backend.domain.A_sample.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleCommonException;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;
import stanl_2.final_backend.domain.A_sample.query.repository.SampleMapper;

@Slf4j
@Service
public class SampleQueryServiceImpl implements SampleQueryService {

    private final SampleMapper sampleMapper;

    @Autowired
    public SampleQueryServiceImpl(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public String selectSampleName(String id) {

        String name = sampleMapper.selectNameById(id);;

        if(name == null){
            throw new SampleCommonException(SampleErrorCode.SAMPLE_NOT_FOUND);
        }

        return name;
    }

    @Override
    @Transactional(readOnly = true)
    public SampleDTO selectSampleInfo(String id) {

        SampleDTO sampleDTO = sampleMapper.selectById(id);

        if(sampleDTO == null){
            throw new SampleCommonException(SampleErrorCode.SAMPLE_NOT_FOUND);
        }

        return sampleDTO;
    }


}