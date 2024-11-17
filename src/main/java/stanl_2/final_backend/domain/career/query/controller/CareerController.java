package stanl_2.final_backend.domain.career.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.career.query.service.CareerQueryService;

@RestController(value = "queryCareerController")
@RequestMapping("/api/v1/career")
public class CareerController {

    private final CareerQueryService careerQueryService;

    @Autowired
    public CareerController(CareerQueryService careerQueryService) {
        this.careerQueryService = careerQueryService;
    }
}
