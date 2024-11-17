package stanl_2.final_backend.domain.certification.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.certification.query.repository.CertificationMapper;

@Service("queryCertificationService")
public class CertificationQueryServiceImpl implements CertificationQueryService {

    private final CertificationMapper certificationMapper;

    @Autowired
    public CertificationQueryServiceImpl(CertificationMapper certificationMapper) {
        this.certificationMapper = certificationMapper;
    }
}
