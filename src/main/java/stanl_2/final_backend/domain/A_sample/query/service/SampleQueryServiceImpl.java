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
    public Object selectSampleName(String id) {

        String cacheKey = "myCache::" + id;
        Object cachedData = redisTemplate.opsForValue().get(cacheKey);

        // 캐시에 데이터가 없다면 DB에서 조회하고 캐시에 저장
        if (cachedData == null) {
            System.out.println("데이터베이스에서 데이터 조회 중...");
            String entity = sampleMapper.selectNameById(id);
            if (entity != null) { // null 체크
                cachedData = entity;
                redisTemplate.opsForValue().set(cacheKey, cachedData); // Redis에 저장
            }
        } else {
            System.out.println("캐시에서 데이터 조회 중...");
        }
        return cachedData;

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
