package stanl_2.final_backend.domain.education.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.education.command.application.service.EducationCommandService;

@RestController("commandEducationController")
@RequestMapping("/api/v1/education")
public class EducationController {

    private final EducationCommandService educationCommandService;

    @Autowired
    public EducationController(EducationCommandService educationCommandService) {
        this.educationCommandService = educationCommandService;
    }
}
