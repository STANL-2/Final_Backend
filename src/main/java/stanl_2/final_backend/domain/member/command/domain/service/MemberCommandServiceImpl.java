package stanl_2.final_backend.domain.member.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.command.application.dto.MemberDetailRegisterDTO;
import stanl_2.final_backend.domain.member.command.application.service.MemberCommandService;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.MemberDetail;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberDetailRepository;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;

@Slf4j
@Service("commandMemberService")
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final AESUtils aesUtils;
    private final MemberDetailRepository memberDetailRepository;

    @Autowired
    public MemberCommandServiceImpl(MemberRepository memberRepository,
                                    ModelMapper modelMapper,
                                    AESUtils aesUtils, MemberDetailRepository memberDetailRepository) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.aesUtils = aesUtils;
        this.memberDetailRepository = memberDetailRepository;
    }

    @Override
    @Transactional
    public void registerMemberDetail(MemberDetailRegisterDTO memberDetailRegisterDTO) throws GeneralSecurityException {

        memberDetailRegisterDTO.setMemberDetailIdentNo(aesUtils.encrypt(memberDetailRegisterDTO.getMemberDetailIdentNo()));
        memberDetailRegisterDTO.setMemberDetailPhone(aesUtils.encrypt(memberDetailRegisterDTO.getMemberDetailPhone()));
        memberDetailRegisterDTO.setMemberDetailBirth(aesUtils.encrypt(memberDetailRegisterDTO.getMemberDetailBirth()));
        memberDetailRegisterDTO.setMemberDetailCertificationScore(aesUtils.encrypt(memberDetailRegisterDTO.getMemberDetailCertificationScore()));
        memberDetailRegisterDTO.setMemberDetailCertificationInst(aesUtils.encrypt(memberDetailRegisterDTO.getMemberDetailCertificationName()));
        memberDetailRegisterDTO.setMemberDetailCareerInfo(aesUtils.encrypt(memberDetailRegisterDTO.getMemberDetailCareerInfo()));

        MemberDetail memberDetail = modelMapper.map(memberDetailRegisterDTO, MemberDetail.class);

        memberDetailRepository.save(memberDetail);
    }
}
