package stanl_2.final_backend.domain.family.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.family.query.service.FamilyQueryService;

@RestController(value = "queryFamilyController")
@RequestMapping("/api/v1/family")
public class FamilyController {

    private final FamilyQueryService familyQueryService;

    @Autowired
    public FamilyController(FamilyQueryService familyQueryService) {
        this.familyQueryService = familyQueryService;
    }
}
