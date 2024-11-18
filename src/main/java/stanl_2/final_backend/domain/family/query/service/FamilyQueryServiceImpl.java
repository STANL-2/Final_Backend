package stanl_2.final_backend.domain.family.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.family.query.repository.FamilyMapper;
import stanl_2.final_backend.global.utils.AESUtils;

@Service("queryFamilyService")
public class FamilyQueryServiceImpl implements FamilyQueryService {

    private final FamilyMapper familyMapper;
    private final AESUtils aesUtils;

    @Autowired
    public FamilyQueryServiceImpl(FamilyMapper familyMapper,
                                  AESUtils aesUtils) {
        this.familyMapper = familyMapper;
        this.aesUtils = aesUtils;
    }
}
