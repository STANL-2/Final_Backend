package stanl_2.final_backend.domain.family.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.family.command.application.service.FamilyCommandService;

@RestController("commandFamilyController")
@RequestMapping("/api/v1/family")
public class FamilyController {

    private final FamilyCommandService familyCommandService;

    @Autowired
    public FamilyController(FamilyCommandService familyCommandService) {
        this.familyCommandService = familyCommandService;
    }
}
