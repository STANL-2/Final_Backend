package stanl_2.final_backend.domain.career.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.career.query.repository.CareerMapper;

@Service("queryCareerService")
public class CareerQueryServiceImpl implements CareerQueryService {

    private final CareerMapper careerMapper;

    @Autowired
    public CareerQueryServiceImpl(CareerMapper careerMapper) {
        this.careerMapper = careerMapper;
    }
}
