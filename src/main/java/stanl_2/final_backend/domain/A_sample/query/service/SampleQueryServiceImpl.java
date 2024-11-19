package stanl_2.final_backend.domain.A_sample.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.command.domain.aggregate.entity.Sample;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleCommonException;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;
import stanl_2.final_backend.domain.A_sample.query.repository.SampleMapper;

import java.util.Optional;

@Slf4j
@Service
public class SampleQueryServiceImpl implements SampleQueryService {

    private final SampleMapper sampleMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SampleQueryServiceImpl(SampleMapper sampleMapper, RedisTemplate<String, Object> redisTemplate) {
        this.sampleMapper = sampleMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public String selectSampleName(Object id) {

        Object name = redisTemplate.opsForValue().get("myCache::" + id);

        if (name == null) {
            System.out.println("데이터베이스에서 데이터 조회 중...");
            Optional<Sample> entity = sampleMapper.selectNameById(id);
            if (entity.isPresent()) {
                name = entity.get();
                redisTemplate.opsForValue().set("myCache::" + id, entity);
            }
        } else {
            System.out.println("캐시에서 데이터 조회 중...");
            if(name == null){
                throw new SampleCommonException(SampleErrorCode.SAMPLE_NOT_FOUND);
            }
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
