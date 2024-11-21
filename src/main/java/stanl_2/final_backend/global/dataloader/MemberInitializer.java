package stanl_2.final_backend.global.dataloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;

@Component
public class MemberInitializer implements ApplicationRunner {

    private final AuthCommandService authCommandService;

    @Autowired
    public MemberInitializer(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 영업 관리자(회원가입)
        SignupDTO employee = new SignupDTO();
        employee.setLoginId("employee");
        employee.setPassword("employee");
        employee.setName("영업 관리자");
        employee.setEmail("employee@stanl.com");
        employee.setAge(3);
        employee.setSex("FEMALE");
        employee.setIdentNo("000000-0000003");
        employee.setPhone("010-0000-0003");
        employee.setAddress("신대방삼거리");
        employee.setPosition("영업 관리자");
        employee.setGrade("고졸");
        employee.setJobType("백엔드 개발자");
        employee.setMilitary("미필");
        employee.setBankName("한국은행");
        employee.setAccount("000-0000-000000-00003");
        employee.setCenterId("CEN_000000001");
        employee.setOrganizationId("ORG_000000001");
        authCommandService.signup(employee);

        // 영업 관리자(권한 부여)
        GrantDTO grandEmployee = new GrantDTO();
        grandEmployee.setLoginId("employee");
        grandEmployee.setRole("EMPLOYEE");
        authCommandService.grantAuthority(grandEmployee);


        // 영업 관리자(회원가입)
        SignupDTO admin = new SignupDTO();
        admin.setLoginId("admin");
        admin.setPassword("admin");
        admin.setName("영업 관리자");
        admin.setEmail("admin@stanl.com");
        admin.setAge(2);
        admin.setSex("FEMALE");
        admin.setIdentNo("000000-0000002");
        admin.setPhone("010-0000-0002");
        admin.setAddress("신대방삼거리");
        admin.setPosition("영업 관리자");
        admin.setGrade("고졸");
        admin.setJobType("백엔드 개발자");
        admin.setMilitary("미필");
        admin.setBankName("한국은행");
        admin.setAccount("000-0000-000000-00000");
        admin.setCenterId("CEN_000000001");
        admin.setOrganizationId("ORG_000000001");
        authCommandService.signup(admin);

        // 영업 관리자(권한 부여)
        GrantDTO grandAdmin = new GrantDTO();
        grandAdmin.setLoginId("admin");
        grandAdmin.setRole("ADMIN");
        authCommandService.grantAuthority(grandAdmin);


        // 영업 담당자(회원가입)
        SignupDTO director = new SignupDTO();
        director.setLoginId("director");
        director.setPassword("director");
        director.setName("영업 담당자");
        director.setEmail("director@stanl.com");
        director.setAge(1);
        director.setSex("MALE");
        director.setIdentNo("000000-0000001");
        director.setPhone("010-0000-0001");
        director.setAddress("STANL2");
        director.setPosition("영업담당자");
        director.setGrade("고졸");
        director.setJobType("영업 담당자");
        director.setMilitary("미필");
        director.setBankName("한국은행");
        director.setAccount("000-0000-000000-00000");
        director.setCenterId("CEN_000000001");
        director.setOrganizationId("ORG_000000001");
        authCommandService.signup(director);

        // 영업 담당자(권한 부여)
        GrantDTO grandDirector = new GrantDTO();
        grandDirector.setLoginId("director");
        grandDirector.setRole("DIRECTOR");
        authCommandService.grantAuthority(grandDirector);


        // 시스템 관리자(회원가입)
        SignupDTO god = new SignupDTO();
        god.setLoginId("god");
        god.setPassword("god");
        god.setName("시스템관리자");
        god.setEmail("god@stanl.com");
        god.setAge(0);
        god.setSex("MALE");
        god.setIdentNo("000000-0000000");
        god.setPhone("010-0000-0000");
        god.setAddress("신대방삼거리");
        god.setPosition("시스템관리자");
        god.setGrade("중졸");
        god.setJobType("백엔드 개발자");
        god.setMilitary("미필");
        god.setBankName("한국은행");
        god.setAccount("000-0000-000000-00000");
        god.setCenterId("CEN_000000001");
        god.setOrganizationId("ORG_000000001");
        authCommandService.signup(god);

        // 시스템 관리자(권한 부여)
        GrantDTO grandGOD = new GrantDTO();
        grandGOD.setLoginId("god");
        grandGOD.setRole("GOD");
        authCommandService.grantAuthority(grandGOD);
    }
}
