package stanl_2.final_backend.domain.certification.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.certification.command.application.service.CertificationCommandService;

@RestController("commandCertificationController")
@RequestMapping("/api/v1/certification")
public class CertificationController {

    private final CertificationCommandService certificationCommandService;

    @Autowired
    public CertificationController(CertificationCommandService certificationCommandService) {
        this.certificationCommandService = certificationCommandService;
    }
}
