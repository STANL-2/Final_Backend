package stanl_2.final_backend.domain.family.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.family.query.dto.FamilyDTO;
import stanl_2.final_backend.domain.family.query.repository.FamilyMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.global.utils.AESUtils;

import java.util.List;

@Service("queryFamilyService")
public class FamilyQueryServiceImpl implements FamilyQueryService {

    private final FamilyMapper familyMapper;
    private final AESUtils aesUtils;
    private final AuthQueryService authQueryService;

    @Autowired
    public FamilyQueryServiceImpl(FamilyMapper familyMapper,
                                  AESUtils aesUtils,
                                  AuthQueryService authQueryService) {
        this.familyMapper = familyMapper;
        this.aesUtils = aesUtils;
        this.authQueryService = authQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyDTO> selectFamilyList(String loginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        List<FamilyDTO> familyList = familyMapper.selectFamilyInfo(memberId);

        return familyList;
    }
}
