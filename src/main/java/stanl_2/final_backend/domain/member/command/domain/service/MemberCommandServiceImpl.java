package stanl_2.final_backend.domain.member.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.member.command.application.service.MemberCommandService;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.global.utils.AESUtils;


@Slf4j
@Service("commandMemberService")
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final AESUtils aesUtils;

    @Autowired
    public MemberCommandServiceImpl(MemberRepository memberRepository,
                                    ModelMapper modelMapper,
                                    AESUtils aesUtils) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.aesUtils = aesUtils;
    }

}
