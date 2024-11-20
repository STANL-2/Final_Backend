package stanl_2.final_backend.domain.organization.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.organization.command.application.service.OrganizationCommandService;

@RestController("commandOrganizationController")
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    private final OrganizationCommandService organizationCommandService;

    @Autowired
    public OrganizationController(OrganizationCommandService organizationCommandService) {
        this.organizationCommandService = organizationCommandService;
    }
}
