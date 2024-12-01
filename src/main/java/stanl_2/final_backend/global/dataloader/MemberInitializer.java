//package stanl_2.final_backend.global.dataloader;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
//import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
//import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
//import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
//import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
//
//@Slf4j
//@Component
//public class MemberInitializer implements ApplicationRunner {
//
//    private final AuthCommandService authCommandService;
//    private final MemberRepository memberRepository;
//
//    @Autowired
//    public MemberInitializer(AuthCommandService authCommandService,
//                             MemberRepository memberRepository) {
//        this.authCommandService = authCommandService;
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        createOrUpdateMember(
//                "employee",
//                "employee",
//                "영업 관리자",
//                "employee@stanl.com",
//                3,
//                "FEMALE",
//                "000000-0000003",
//                "010-0000-0003",
//                "신대방삼거리",
//                "영업 관리자",
//                "고졸",
//                "백엔드 개발자",
//                "미필",
//                "한국은행",
//                "000-0000-000000-00003",
//                "CEN_000000001",
//                "ORG_000000001",
//                "EMPLOYEE"
//        );
//
//        createOrUpdateMember(
//                "admin",
//                "admin",
//                "영업 관리자",
//                "admin@stanl.com",
//                2,
//                "FEMALE",
//                "000000-0000002",
//                "010-0000-0002",
//                "신대방삼거리",
//                "영업 관리자",
//                "고졸",
//                "백엔드 개발자",
//                "미필",
//                "한국은행",
//                "000-0000-000000-00000",
//                "CEN_000000001",
//                "ORG_000000001",
//                "ADMIN"
//        );
//
//        createOrUpdateMember(
//                "director",
//                "director",
//                "영업 담당자",
//                "director@stanl.com",
//                1,
//                "MALE",
//                "000000-0000001",
//                "010-0000-0001",
//                "STANL2",
//                "영업담당자",
//                "고졸",
//                "영업 담당자",
//                "미필",
//                "한국은행",
//                "000-0000-000000-00000",
//                "CEN_000000001",
//                "ORG_000000001",
//                "DIRECTOR"
//        );
//
//        createOrUpdateMember(
//                "god",
//                "god",
//                "시스템관리자",
//                "god@stanl.com",
//                0,
//                "MALE",
//                "000000-0000000",
//                "010-0000-0000",
//                "신대방삼거리",
//                "시스템관리자",
//                "중졸",
//                "백엔드 개발자",
//                "미필",
//                "한국은행",
//                "000-0000-000000-00000",
//                "CEN_000000001",
//                "ORG_000000001",
//                "GOD"
//        );
//    }
//
//    private void createOrUpdateMember(String loginId,
//                                      String password,
//                                      String name,
//                                      String email,
//                                      int age,
//                                      String sex,
//                                      String identNo,
//                                      String phone,
//                                      String address,
//                                      String position,
//                                      String grade,
//                                      String jobType,
//                                      String military,
//                                      String bankName,
//                                      String account,
//                                      String centerId,
//                                      String organizationId,
//                                      String role) throws Exception {
//
//        // db에 정보가 있는지 확인
//        Member existingMember = memberRepository.findByLoginId(loginId);
//        if (existingMember == null) {
//            // Create the user
//            SignupDTO signupDTO = new SignupDTO();
//            signupDTO.setLoginId(loginId);
//            signupDTO.setPassword(password);
//            signupDTO.setName(name);
//            signupDTO.setEmail(email);
//            signupDTO.setAge(age);
//            signupDTO.setSex(sex);
//            signupDTO.setIdentNo(identNo);
//            signupDTO.setPhone(phone);
//            signupDTO.setAddress(address);
//            signupDTO.setPosition(position);
//            signupDTO.setGrade(grade);
//            signupDTO.setJobType(jobType);
//            signupDTO.setMilitary(military);
//            signupDTO.setBankName(bankName);
//            signupDTO.setAccount(account);
//            signupDTO.setCenterId(centerId);
//            signupDTO.setOrganizationId(organizationId);
//
//            authCommandService.signup(signupDTO);
//
//            // Grant role to the user
//            GrantDTO grantDTO = new GrantDTO();
//            grantDTO.setLoginId(loginId);
//            grantDTO.setRole(role);
//            authCommandService.grantAuthority(grantDTO);
//
//            log.info("{} 유저를 {} 역할로 생성합니다.", loginId, role);
//        } else {
//            log.info("{} 유저 정보가 이미 존재합니다.", loginId);
//        }
//    }
//}
