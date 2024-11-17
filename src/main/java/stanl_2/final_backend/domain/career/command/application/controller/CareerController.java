package stanl_2.final_backend.domain.career.command.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.career.command.application.service.CareerCommandService;

@Slf4j
@RestController("commandCareerController")
@RequestMapping("/api/v1/career")
public class CareerController {

    private final CareerCommandService careerCommandService;

    @Autowired
    public CareerController(CareerCommandService careerCommandService) {
        this.careerCommandService = careerCommandService;
    }
}
