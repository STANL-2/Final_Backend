package stanl_2.final_backend.global.dataloader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Random;

@Slf4j
@Component
public class MemberInitializer implements ApplicationRunner {

    private final AuthCommandService authCommandService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberInitializer(AuthCommandService authCommandService,
                             MemberRepository memberRepository) {
        this.authCommandService = authCommandService;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 우리 계정
        createOrUpdateMember(
                "god",
                "god",
                "신하늘",
                "god@stanl.com",
                0,
                "MALE",
                "000000-0000000",
                "010-0000-0000",
                "서울 동작구 보라매로 87",
                "시스템 관리자",
                "중졸",
                "REGULAR",
                "미필",
                "한국은행",
                "000-0000-000000-00000",
                "CEN_000000000",
                "ORG_000000000",
                "GOD",
                loadImage("god.png")
        );

        // 심사위원 1 계정
        createOrUpdateMember(
                "god1",
                "god1",
                "이름1",
                "god@stanl.com",
                0,
                "MALE",
                "000000-0000000",
                "010-0000-0000",
                "서울 동작구 보라매로 87",
                "심사위원",
                "중졸",
                "TEMPORARY",
                "미필",
                "한국은행",
                "000-0000-000000-00000",
                "CEN_000000000",
                "ORG_000000000",
                "GOD",
                loadImage("god.png")
        );

        // 심사위원 2 계정
        createOrUpdateMember(
                "god2",
                "god2",
                "이름2",
                "god@stanl.com",
                0,
                "MALE",
                "000000-0000000",
                "010-0000-0000",
                "서울 동작구 보라매로 87",
                "심사위원",
                "중졸",
                "TEMPORARY",
                "미필",
                "한국은행",
                "000-0000-000000-00000",
                "CEN_000000000",
                "ORG_000000000",
                "GOD",
                loadImage("god.png")
        );

        // 심사위원 3 계정
        createOrUpdateMember(
                "god3",
                "god3",
                "이름3",
                "god@stanl.com",
                0,
                "MALE",
                "000000-0000000",
                "010-0000-0000",
                "서울 동작구 보라매로 87",
                "심사위원",
                "중졸",
                "TEMPORARY",
                "미필",
                "한국은행",
                "000-0000-000000-00000",
                "CEN_000000000",
                "ORG_000000000",
                "GOD",
                loadImage("god.png")
        );

        Random random = new Random();
        String[] positions = {"Staff", "Manager", "Director", "Executive"};
        String[] grades = {"A", "B", "C", "D"};
        String[] jobTypes = {"REGULAR", "TEMPORARY"};
        String[] militaryStatus = {"fulfilled", "exemption", "unfulfilled"};
        String[] genders = {"MALE", "FEMALE"};
        String[] roles = {"EMPLOYEE", "ADMIN", "DIRECTOR"};
        String[] lastNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
        String[] firstNames = {"민수", "지훈", "서연", "예준", "하은", "도현", "지원", "유진", "현우", "수아"};
        String[] addresses = {
                "서울특별시 강남구 테헤란로",
                "부산광역시 해운대구 센텀중앙로",
                "대구광역시 수성구 범어로",
                "인천광역시 남동구 미래로",
                "광주광역시 서구 상무대로",
                "대전광역시 유성구 대덕대로",
                "울산광역시 남구 번영로",
                "경기도 성남시 분당구 판교로",
                "강원도 춘천시 중앙로",
                "충청북도 청주시 상당구 상당로"
        };


        for (int i = 1; i <= 100; i++) {
            int centerId = random.nextInt(10) + 1; // Center ID between 0-10
            int orgId = random.nextInt(10) + 1; // Org ID between 0-10
            String sex = genders[i % 2];
            String name = lastNames[random.nextInt(lastNames.length)] + firstNames[random.nextInt(firstNames.length)];
            String address = addresses[random.nextInt(addresses.length)];


            createOrUpdateMember(
                    "user" + i,
                    "pass" + i,
                    name,
                    "user" + i + "@example.com",
                    20 + random.nextInt(30), // Random age between 20-49
                    sex,
                    String.format("123456-%07d", 1000000 + random.nextInt(9000000)),
                    "010-1234-567" + i,
                    address,
                    positions[random.nextInt(positions.length)], // Random position
                    grades[random.nextInt(grades.length)], // Random grade
                    jobTypes[random.nextInt(jobTypes.length)], // Random job type
                    militaryStatus[random.nextInt(militaryStatus.length)], // Random military status
                    "Bank" + centerId,
                    "123456789" + i,
                    String.format("CEN_%09d", centerId),
                    String.format("ORG_%09d", orgId),
                    roles[random.nextInt(roles.length)], // Random role
                    loadImage("default.png")
            );
        }

    }

    private MultipartFile loadImage(String fileName) throws IOException {
        // 리소스 디렉토리에서 파일 로드
        ClassPathResource resource = new ClassPathResource("image/" + fileName);

        // 파일 내용을 byte 배열로 읽기
        byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());

        // MultipartFile 익명 구현체 생성
        return new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                try {
                    // 파일의 MIME 타입 결정
                    return Files.probeContentType(resource.getFile().toPath());
                } catch (IOException e) {
                    return "application/octet-stream"; // 기본 MIME 타입
                }
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), fileContent);
            }
        };
    }


    private void createOrUpdateMember(String loginId,
                                      String password,
                                      String name,
                                      String email,
                                      int age,
                                      String sex,
                                      String identNo,
                                      String phone,
                                      String address,
                                      String position,
                                      String grade,
                                      String jobType,
                                      String military,
                                      String bankName,
                                      String account,
                                      String centerId,
                                      String organizationId,
                                      String role,
                                      MultipartFile imageUrl) throws Exception {

        // db에 정보가 있는지 확인
        Member existingMember = memberRepository.findByLoginId(loginId);
        if (existingMember == null) {
            // Create the user
            SignupDTO signupDTO = new SignupDTO();
            signupDTO.setLoginId(loginId);
            signupDTO.setPassword(password);
            signupDTO.setName(name);
            signupDTO.setEmail(email);
            signupDTO.setAge(age);
            signupDTO.setSex(sex);
            signupDTO.setIdentNo(identNo);
            signupDTO.setPhone(phone);
            signupDTO.setAddress(address);
            signupDTO.setPosition(position);
            signupDTO.setGrade(grade);
            signupDTO.setJobType(jobType);
            signupDTO.setMilitary(military);
            signupDTO.setBankName(bankName);
            signupDTO.setAccount(account);
            signupDTO.setCenterId(centerId);
            signupDTO.setOrganizationId(organizationId);

            authCommandService.signup(signupDTO, imageUrl);

            // Grant role to the user
            GrantDTO grantDTO = new GrantDTO();
            grantDTO.setLoginId(loginId);
            grantDTO.setRole(role);
            authCommandService.grantAuthority(grantDTO);

            log.info("{} 유저를 {} 역할로 생성합니다.", loginId, role);
        } else {
            log.info("{} 유저 정보가 이미 존재합니다.", loginId);
        }
    }
}
