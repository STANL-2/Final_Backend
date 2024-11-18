package stanl_2.final_backend.domain.education.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.education.query.service.EducationQueryService;

@RestController(value = "queryEducationController")
@RequestMapping("/api/v1/education")
public class EducationController {

    private final EducationQueryService educationQueryService;

    @Autowired
    public EducationController(EducationQueryService educationQueryService) {
        this.educationQueryService = educationQueryService;
    }
}
