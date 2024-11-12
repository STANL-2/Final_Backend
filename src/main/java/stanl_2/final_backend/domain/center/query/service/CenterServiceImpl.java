package stanl_2.final_backend.domain.center.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.common.util.RequestList;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.repository.CenterMapper;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("queryCenterServiceImpl")
public class CenterServiceImpl implements CenterService{

    private final CenterMapper centerMapper;

    @Autowired
    public CenterServiceImpl(CenterMapper centerMapper) {
        this.centerMapper = centerMapper;
    }

    @Override
    @Transactional
    public CenterSelectIdDTO selectByCenterId(String id) {

        CenterSelectIdDTO centerSelectIdDTO = centerMapper.findCenterById(id);

        return centerSelectIdDTO;
    }

    @Override
    @Transactional
    public Page<Map<String, Object>> selectAll(Pageable pageable) {

        RequestList<?> requestList = RequestList.builder()
                .pageable(pageable)
                .build();

        List<Map<String, Object>> centerList = centerMapper.findCenterAll(requestList);

        int total = centerMapper.findCenterCount();

        return new PageImpl<>(centerList, pageable, total);
    }

    @Override
    @Transactional
    public Page<Map<String, Object>> selectBySearch(Map<String, Object> params){

        Pageable pageable = (Pageable) params.get("pageable");

        List<Map<String, Object>> centerList = centerMapper.findCenterBySearch(params);

        int total = centerMapper.findCenterBySearchCount(params);

        return new PageImpl<>(centerList, pageable, total);
    }


}
