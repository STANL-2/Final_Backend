package stanl_2.final_backend.domain.education.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.certification.query.dto.CertificationDTO;
import stanl_2.final_backend.domain.education.query.dto.EducationDTO;
import stanl_2.final_backend.domain.education.query.repository.EducationMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.util.List;

@Service("queryEducationService")
public class EducationQueryServiceImpl implements EducationQueryService{

    private final EducationMapper educationMapper;
    private final AuthQueryService authQueryService;

    @Autowired
    public EducationQueryServiceImpl(EducationMapper educationMapper,
                                     AuthQueryService authQueryService) {
        this.educationMapper = educationMapper;
        this.authQueryService = authQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationDTO> selectEducationList(String loginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        List<EducationDTO> educationList = educationMapper.selectEducationInfo(memberId);

        return educationList;
    }
}
