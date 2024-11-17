package stanl_2.final_backend.domain.certification.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.certification.query.service.CertificationQueryService;

@RestController(value = "queryCertificationController")
@RequestMapping("/api/v1/certification")
public class CertificationController {

    private final CertificationQueryService certificationQueryService;

    @Autowired
    public CertificationController(CertificationQueryService certificationQueryService) {
        this.certificationQueryService = certificationQueryService;
    }
}
