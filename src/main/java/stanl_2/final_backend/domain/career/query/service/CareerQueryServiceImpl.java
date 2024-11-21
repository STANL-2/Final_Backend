package stanl_2.final_backend.domain.career.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.career.query.dto.CareerDTO;
import stanl_2.final_backend.domain.career.query.repository.CareerMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.util.List;

@Slf4j
@Service("queryCareerService")
public class CareerQueryServiceImpl implements CareerQueryService {

    private final CareerMapper careerMapper;
    private final AuthQueryService authQueryService;

    @Autowired
    public CareerQueryServiceImpl(CareerMapper careerMapper,
                                  AuthQueryService authQueryService) {
        this.careerMapper = careerMapper;
        this.authQueryService = authQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareerDTO> selectCareerList(String loginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        List<CareerDTO> careerList = careerMapper.selectCareerInfo(memberId);

        return careerList;
    }
}
