package stanl_2.final_backend.domain.center.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleCommonException;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;
import stanl_2.final_backend.domain.center.common.util.RequestList;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.repository.CenterMapper;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("queryCenterServiceImpl")
public class CenterServiceImpl implements CenterService{

    private final CenterMapper centerMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CenterServiceImpl(CenterMapper centerMapper, RedisTemplate<String, Object> redisTemplate) {
        this.centerMapper = centerMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public CenterSelectIdDTO selectByCenterId(String id) {

        CenterSelectIdDTO centerSelectIdDTO = centerMapper.findCenterById(id);

        return centerSelectIdDTO;
    }

    @Override
    @Transactional
    public Page<CenterSelectAllDTO> selectAll(Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        List<CenterSelectAllDTO> centerList = centerMapper.findCenterAll(size, offset);

        int total = centerMapper.findCenterCount();

        return new PageImpl<>(centerList, pageable, total);
    }

    @Override
    @Transactional
    public Page<CenterSelectAllDTO> selectBySearch(CenterSearchRequestDTO centerSearchRequestDTO, Pageable pageable){
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

//        String cacheKey = "myCache::center::offset=" + offset + "::size=" + size;
//
//        List<CenterSelectAllDTO> centerList = (List<CenterSelectAllDTO>) redisTemplate.opsForValue().get(cacheKey);
//        if (centerList == null) {
//            System.out.println("여기까진 들어가나?");
//            centerList = centerMapper.findCenterBySearch(size, offset, centerSearchRequestDTO);
//            redisTemplate.opsForValue().set(cacheKey, centerList);
//        }

        List<CenterSelectAllDTO> centerList = centerMapper.findCenterBySearch(size, offset, centerSearchRequestDTO);
        int total = centerMapper.findCenterBySearchCount(centerSearchRequestDTO);

        return new PageImpl<>(centerList, pageable, total);
    }


}
