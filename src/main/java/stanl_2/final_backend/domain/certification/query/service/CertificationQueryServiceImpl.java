package stanl_2.final_backend.domain.certification.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.certification.query.dto.CertificationDTO;
import stanl_2.final_backend.domain.certification.query.repository.CertificationMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.util.List;

@Service("queryCertificationService")
public class CertificationQueryServiceImpl implements CertificationQueryService {

    private final CertificationMapper certificationMapper;
    private final AuthQueryService authQueryService;

    @Autowired
    public CertificationQueryServiceImpl(CertificationMapper certificationMapper,
                                         AuthQueryService authQueryService) {
        this.certificationMapper = certificationMapper;
        this.authQueryService = authQueryService;
    }

    @Override
    @Transactional
    public List<CertificationDTO> selectCertificationList(String id) {

        String loginId = authQueryService.selectMemberIdByLoginId(id);

        List<CertificationDTO> certificationList = certificationMapper.selectCertificationInfo(loginId);

        return certificationList;
    }
}
