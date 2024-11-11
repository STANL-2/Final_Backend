package stanl_2.final_backend.domain.center.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.repository.CenterMapper;

@Slf4j
@Service("queryCenterServiceImpl")
public class CenterServiceImpl implements CenterService{

    private final CenterMapper centerMapper;

    @Autowired
    public CenterServiceImpl(CenterMapper centerMapper) {
        this.centerMapper = centerMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CenterSelectIdDTO selectByCenterId(Long centerId) {

        CenterSelectIdDTO centerSelectIdDTO = centerMapper.findCenterById(centerId);

        return centerSelectIdDTO;
    }
}
