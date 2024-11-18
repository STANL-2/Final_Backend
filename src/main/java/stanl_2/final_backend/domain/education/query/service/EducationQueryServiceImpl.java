package stanl_2.final_backend.domain.education.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.education.query.repository.EducationMapper;

@Service("queryEducationService")
public class EducationQueryServiceImpl implements EducationQueryService{

    private final EducationMapper educationMapper;

    @Autowired
    public EducationQueryServiceImpl(EducationMapper educationMapper) {
        this.educationMapper = educationMapper;
    }
}
