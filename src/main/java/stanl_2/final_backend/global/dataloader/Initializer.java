package stanl_2.final_backend.global.dataloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.career.command.domain.aggregate.entity.Career;
import stanl_2.final_backend.domain.career.command.domain.repository.CareerRepository;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistDTO;
import stanl_2.final_backend.domain.center.command.application.service.CenterCommandService;
import stanl_2.final_backend.domain.certification.command.domain.aggregate.entity.Certification;
import stanl_2.final_backend.domain.certification.command.domain.repository.CertificationRepository;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.service.CustomerCommandService;
import stanl_2.final_backend.domain.education.command.domain.aggregate.entity.Education;
import stanl_2.final_backend.domain.education.command.domain.repository.EducationRepository;
import stanl_2.final_backend.domain.family.command.domain.aggregate.entity.Family;
import stanl_2.final_backend.domain.family.command.domain.repository.FamilyRepository;
import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.command.domain.repository.MemberRepository;
import stanl_2.final_backend.domain.organization.command.domain.aggregate.entity.Organization;
import stanl_2.final_backend.domain.organization.command.domain.repository.OrganizationRepository;
import stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity.Product;
import stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity.ProductOption;
import stanl_2.final_backend.domain.product.command.application.domain.repository.ProductOptionRepository;
import stanl_2.final_backend.domain.product.command.application.domain.repository.ProductRepository;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleRegistDTO;
import stanl_2.final_backend.domain.schedule.command.application.service.ScheduleCommandService;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;
import stanl_2.final_backend.domain.schedule.command.domain.repository.ScheduleRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class Initializer implements ApplicationRunner {

    private final AuthCommandService authCommandService;

    private final MemberRepository memberRepository;
    private final CareerRepository careerRepository;
    private final CertificationRepository certificationRepository;
    private final EducationRepository educationRepository;
    private final FamilyRepository familyRepository;
    private final OrganizationRepository organizationRepository;
    private final CustomerCommandService customerCommandService;
    private final CenterCommandService centerCommandService;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    private final ScheduleCommandService scheduleCommandService;

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
        String[] positions = {"EMPOLYEE", "ADMIN", "DIRECTOR"};
        String[] grades = {"A", "B", "C", "D"};
        String[] jobTypes = {"REGULAR", "TEMPORARY"};
        String[] militaryStatus = {"fulfilled", "exemption", "unfulfilled"};
        String[] genders = {"MALE", "FEMALE"};
        String[] roles = {"EMPLOYEE", "ADMIN", "DIRECTOR"};
        String[] lastNames = {"김", "이", "박", "최", "정", "강", "조", "유", "윤", "장", "임", "기", "방", "하", "도", "한", "손", "송", "오", "조", "서", "배", "홍", "류", "신", "권", "곽"};
        String[] firstNames = {"민수", "지훈", "서연", "예준", "하은", "도현", "지원", "유진", "현우", "수아", "수빈", "주희", "지아", "준호", "혜린", "지민", "은지", "시우", "다영", "태현", "연우"};
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


        // 회원 및 역할 생성 로직
        for (int i = 1; i <= 96; i++) {
            int centerId = random.nextInt(10) + 1;
            int orgId = random.nextInt(10) + 5;
            String sex = genders[i % 2];
            String name = lastNames[random.nextInt(lastNames.length)] + firstNames[random.nextInt(firstNames.length)];
            String address = addresses[random.nextInt(addresses.length)];

            createOrUpdateMember(
                    String.format("M%09d", i),
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


        // 영업 관련 경력
        String[] salesCareers = {
                "영업 대표",
                "지역 영업 관리자",
                "계정 관리자",
                "영업 컨설턴트",
                "사업 개발 관리자",
                "영업 코디네이터",
                "영업 지역 관리자",
                "영업 엔지니어",
                "내부 영업 대표",
                "주요 계정 관리자",
                "전국 영업 관리자",
                "영업 이사",
                "소매 영업 사원",
                "제약 영업 대표",
                "자동차 영업 컨설턴트",
                "영업 분석가",
                "현장 영업 대표",
                "선임 영업 임원",
                "채널 영업 관리자",
                "영업 운영 관리자",
                "기술 영업 대표",
                "영업 교육 담당자",
                "디지털 영업 전문가",
                "광고 영업 임원",
                "미디어 영업 컨설턴트",
                "B2B 영업 전문가",
                "전자상거래 영업 관리자",
                "기업 영업 관리자",
                "리드 생성 전문가",
                "수출 영업 관리자",
                "프랜차이즈 영업 컨설턴트",
                "보험 영업 대리인",
                "금융 영업 상담사",
                "소프트웨어 영업 대표",
                "SaaS 영업 관리자",
                "제품 영업 전문가",
                "영업 계정 관리자",
                "영업 개발 대표",
                "부동산 영업 대리인",
                "건설 영업 임원",
                "헬스케어 영업 컨설턴트",
                "산업 영업 대표",
                "도매 영업 관리자",
                "영업 관계 관리자",
                "클라이언트 참여 전문가",
                "영업 협상가",
                "영업 리드 코디네이터",
                "럭셔리 상품 영업 상담사",
                "호스피탈리티 영업 임원",
                "영업 지역 계정 임원"
        };

        // Career(경력) 저장
        for (int i = 1; i <= 100; i++) {
            String memberId = String.format("MEM_%09d", i);
            for (int j = 0; j < 4; j++) {
                Career newCareer = new Career();
                newCareer.setEmplDate(getRandomEmploymentDate());
                newCareer.setResignDate(getRandomResignationDate(LocalDate.parse(newCareer.getEmplDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                newCareer.setName(salesCareers[random.nextInt(salesCareers.length)]);
                newCareer.setMemberId(memberId);
                careerRepository.save(newCareer);
            }
        }

        String[][] salesCertifications = {
                {"영업관리사", "한국영업협회"},
                {"마케팅관리사", "대한마케팅협회"},
                {"국제판매전문가", "국제세일즈인증원"},
                {"고객관계관리(CRM) 자격증", "CRM연구소"},
                {"광고판매전문가", "한국광고협회"},
                {"유통관리사", "대한상공회의소"},
                {"물류관리사", "한국물류협회"},
                {"세일즈포스 인증 전문가", "Salesforce"},
                {"디지털마케팅 전문가 자격증", "한국디지털마케팅협회"},
                {"상담판매 자격증", "한국상담판매협회"},
                {"고객서비스전문가", "한국CS관리협회"},
                {"리테일 영업 자격증", "대한리테일협회"},
                {"보험판매 자격증", "대한보험협회"},
                {"제약영업 자격증", "한국제약협회"},
                {"B2B 영업전문가", "한국B2B영업협회"},
                {"자동차판매전문가", "한국자동차판매연합회"},
                {"부동산판매 자격증", "대한부동산협회"},
                {"프랜차이즈 관리 자격증", "프랜차이즈산업협회"},
                {"광고 및 프로모션 자격증", "한국프로모션협회"},
                {"국제 무역영업 자격증", "한국무역협회"},
                {"비즈니스 협상 자격증", "한국협상전문가협회"},
                {"제품관리 전문가", "한국제품관리연구소"},
                {"판매심리학 자격증", "한국심리학회"},
                {"금융상품판매 자격증", "한국금융협회"},
                {"의료기기 영업 자격증", "대한의료기기산업협회"},
                {"공급망 관리 자격증", "한국공급망관리협회"},
                {"소비자행동 분석 자격증", "대한소비자행동분석협회"},
                {"브랜드 관리 자격증", "한국브랜드관리협회"},
                {"전자상거래 마케팅 자격증", "대한전자상거래협회"},
                {"기업 대기업 영업 자격증", "대한기업영업협회"},
                {"퍼포먼스 마케팅 자격증", "한국퍼포먼스마케팅협회"},
                {"클라이언트 관계 관리 자격증", "한국클라이언트관리협회"},
                {"RFP(제안서 작성) 전문가", "한국RFP협회"},
                {"SaaS 판매 전문가", "한국SaaS협회"},
                {"클라우드 솔루션 판매 자격증", "한국클라우드산업협회"},
                {"헬스케어 판매 전문가", "대한헬스케어산업협회"},
                {"텔레마케팅 자격증", "한국텔레마케팅협회"},
                {"제품 발표 및 데모 자격증", "대한제품발표협회"},
                {"전시회 및 이벤트 영업 자격증", "한국전시산업협회"},
                {"기술 영업 전문가", "대한기술영업협회"},
                {"대리점 관리 자격증", "한국대리점관리협회"},
                {"국제 시장 개발 자격증", "국제시장개발연구소"},
                {"영업 전략 기획 자격증", "한국영업전략기획협회"},
                {"신사업 개발 자격증", "대한신사업개발협회"},
                {"리더십 및 팀 관리 자격증", "한국리더십센터"},
                {"상담 영업 전문가", "한국상담영업연구소"},
                {"호스피탈리티 및 관광 영업 자격증", "한국관광협회"},
                {"에너지 산업 영업 전문가", "대한에너지산업협회"},
                {"IT 영업 전문가", "한국IT영업협회"},
                {"소셜 미디어 마케팅 자격증", "대한소셜미디어마케팅협회"}
        };

        // Certification(자격증) 저장
        for (int i = 1; i <= 100; i++) {
            String memberId = String.format("MEM_%09d", i);
            for (int j = 0; j < 4; j++) {
                Certification newCertification = new Certification();
                newCertification.setAcquisitionDate(getRandomEmploymentDate());
                int n = random.nextInt(salesCareers.length);
                newCertification.setAgency(salesCertifications[n][1]);
                newCertification.setName(salesCertifications[n][0]);
                newCertification.setScore(String.valueOf(random.nextInt(101)));
                newCertification.setMemberId(memberId);
                certificationRepository.save(newCertification);
            }
        }

        // 전공
        String[] salesMajors = {
                "마케팅학",
                "광고홍보학",
                "경영학",
                "국제경영학",
                "판매관리학",
                "고객관계관리학",
                "유통물류학",
                "상업교육",
                "비즈니스 커뮤니케이션",
                "디지털 마케팅",
                "국제무역학",
                "브랜드 관리학",
                "소비자 행동 분석학",
                "비즈니스 전략학",
                "리테일 매니지먼트",
                "판매 및 협상학",
                "프랜차이즈 관리학",
                "제품 관리학",
                "광고 및 프로모션학",
                "세일즈 엔지니어링",
                "부동산 판매학",
                "서비스 마케팅",
                "헬스케어 영업학",
                "전자상거래학",
                "금융 영업학",
                "기술 영업학",
                "공급망 관리학",
                "퍼포먼스 마케팅",
                "B2B 영업학",
                "에너지 산업 영업학",
                "커뮤니케이션학",
                "경제학",
                "사회학",
                "심리학",
                "경영정보학",
                "홍보학",
                "미디어학",
                "IT 비즈니스",
                "국제관계학",
                "고객 서비스 관리학",
                "인적 자원 관리학",
                "행동 과학",
                "비즈니스 분석학",
                "창업학",
                "리더십 학",
                "데이터 분석학",
                "디자인 씽킹",
                "공공 관계학",
                "행동 경제학",
                "프로젝트 관리학"
        };

        // 대학
        String[] universities = {
                "서울대학교", "연세대학교", "고려대학교", "성균관대학교", "한양대학교",
                "서강대학교", "중앙대학교", "경희대학교", "이화여자대학교", "한국외국어대학교",
                "건국대학교", "동국대학교", "서울시립대학교", "숙명여자대학교", "숭실대학교",
                "광운대학교", "명지대학교", "국민대학교", "세종대학교", "가톨릭대학교",
                "홍익대학교", "단국대학교", "아주대학교", "인하대학교", "한국항공대학교",
                "경기대학교", "가천대학교", "서울과학기술대학교", "한성대학교", "서울여자대학교",
                "백석대학교", "상명대학교", "한남대학교", "동아대학교", "부산대학교",
                "울산대학교", "부경대학교", "조선대학교", "전남대학교", "전북대학교",
                "제주대학교", "포항공과대학교", "한동대학교", "울산과학기술원", "경북대학교",
                "계명대학교", "영남대학교", "대구대학교", "대구가톨릭대학교", "안동대학교",
                "청주대학교", "충북대학교", "충남대학교", "한서대학교", "공주대학교",
                "강원대학교", "춘천교육대학교", "한국교통대학교", "목포대학교", "목포해양대학교",
                "순천대학교", "순천향대학교", "원광대학교", "남서울대학교", "상지대학교",
                "강릉원주대학교", "한국해양대학교", "창원대학교", "인제대학교", "동의대학교",
                "부산외국어대학교", "신라대학교", "경남대학교", "창신대학교", "고신대학교",
                "광주대학교", "호남대학교", "동신대학교", "전주대학교", "배재대학교",
                "목원대학교", "백석문화대학교", "한밭대학교", "세명대학교", "중부대학교",
                "한국산업기술대학교", "경운대학교", "대진대학교", "한라대학교", "제주국제대학교",
                "협성대학교", "평택대학교", "가야대학교", "강남대학교", "건양대학교",
                "경동대학교", "경민대학교", "경북과학대학교", "경인여자대학교", "경인교육대학교"
        };

        // Education(학력) 저장
        for (int i = 1; i < 100; i++) {
            String memberId = String.format("MEM_%09d", i);
            Education newEducation = new Education();
            newEducation.setEntranceDate(getRandomEmploymentDate());
            newEducation.setGraduationDate(getRandomResignationDate(LocalDate.parse(newEducation.getEntranceDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            newEducation.setMajor(salesMajors[random.nextInt(salesMajors.length)]);
            newEducation.setName(universities[random.nextInt(universities.length)]);
            newEducation.setScore(String.valueOf(2.0 + (Math.random() * (4.5 - 2.0))));
            newEducation.setMemId(memberId);
            educationRepository.save(newEducation);
        }

        // 관계
        String[] familyRelations = {
                "아버지", "어머니", "형", "누나", "남동생",
                "여동생", "할아버지", "할머니", "외할아버지", "외할머니",
                "삼촌", "이모", "고모", "작은아버지", "작은어머니",
                "큰아버지", "큰어머니", "사촌형", "사촌누나", "사촌동생",
                "조카", "손자", "손녀", "시아버지", "시어머니",
                "장인", "장모", "매형", "매제", "형부",
                "올케", "처남", "처형", "형수", "제수",
                "아들", "딸", "손자", "손녀"
        };


        LocalDate randomBirthDate = LocalDate.of(
                ThreadLocalRandom.current().nextInt(1980, 2024),  // 1980년부터 2023년까지의 무작위 연도
                ThreadLocalRandom.current().nextInt(1, 13),       // 1월부터 12월까지의 무작위 월
                ThreadLocalRandom.current().nextInt(1, 29)        // 1일부터 28일까지의 무작위 일 (안전하게 28일까지 설정)
        );

        String randomPhone = String.format("010-%04d-%04d",
                (int) (Math.random() * 10000),
                (int) (Math.random() * 10000)
        );

        // Family(가족) 저장
        for (int i = 1; i <= 100; i++) {
            String memberId = String.format("MEM_%09d", i);
            for (int j = 0; j < 4; j++) {
                // 주민등록번호 생성
                String birthDateStr = randomBirthDate.format(DateTimeFormatter.ofPattern("yyMMdd")); // YYMMDD 형식
                String genderDigit = Math.random() < 0.5 ? "1" : "2"; // 성별은 1(남) 또는 2(여)
                String randomNumbers = String.format("%04d", (int) (Math.random() * 10000)); // 임의의 4자리 번호
                String checkDigit = String.format("%d", (int) (Math.random() * 10)); // 검증용 마지막 자리는 0~9

                // 주민등록번호 만들기
                String identNo = birthDateStr + genderDigit + randomNumbers + checkDigit;

                Family newFamily = new Family();
                newFamily.setName(lastNames[random.nextInt(lastNames.length)] + firstNames[random.nextInt(firstNames.length)]);
                newFamily.setRelation(familyRelations[random.nextInt(familyRelations.length)]);
                newFamily.setSex(Math.random() < 0.5 ? "MALE" : "FEMALE");
                newFamily.setBirth(birthDateStr);
                newFamily.setPhone(randomPhone);
                newFamily.setDie(Math.random() < 0.5 ? true : false);
                newFamily.setDisability(Math.random() < 0.5 ? true : false);
                newFamily.setIdentNo(identNo);
                newFamily.setMemId(memberId);

                familyRepository.save(newFamily);
            }
        }


        // 부서 저장
        Organization org1 = new Organization("ORG_000000001", "서울 지사", null);
        organizationRepository.save(org1);
        Organization org2 = new Organization("ORG_000000002", "부산 지사", null);
        organizationRepository.save(org2);
        Organization org3 = new Organization("ORG_000000003", "인천 지사", null);
        organizationRepository.save(org3);
        Organization org4 = new Organization("ORG_000000004", "대전 지사", null);
        organizationRepository.save(org4);
        Organization org5 = new Organization("ORG_000000005", "영업부(서울 1팀)", "ORG_000000001");
        organizationRepository.save(org5);
        Organization org6 = new Organization("ORG_000000006", "영업부(서울 2팀)", "ORG_000000001");
        organizationRepository.save(org6);
        Organization org7 = new Organization("ORG_000000007", "영업부(부산 1팀)", "ORG_000000002");
        organizationRepository.save(org7);
        Organization org8 = new Organization("ORG_000000008", "영업부(부산 2팀)", "ORG_000000002");
        organizationRepository.save(org8);
        Organization org9 = new Organization("ORG_000000009", "영업부(인천 1팀)", "ORG_000000003");
        organizationRepository.save(org9);
        Organization org10 = new Organization("ORG_000000010", "영업부(인천 2팀)", "ORG_000000003");
        organizationRepository.save(org10);
        Organization org11 = new Organization("ORG_000000011", "영업부(대전 1팀)", "ORG_000000004");
        organizationRepository.save(org11);
        Organization org12 = new Organization("ORG_000000012", "영업부(대전 2팀)", "ORG_000000004");
        organizationRepository.save(org12);
        Organization org13 = new Organization("ORG_000000013", "마케팅부", "ORG_000000001");
        organizationRepository.save(org13);
        Organization org14 = new Organization("ORG_000000014", "기술부", "ORG_000000001");
        organizationRepository.save(org14);


        // 고객 인당 100명 씩
        for (int i = 1; i <= 100; i++) {
            String memberId = String.format("MEM_%09d", i);
            for (int j = 0; j < 100; j++) {
                String sex = genders[i % 2];
                String name = lastNames[random.nextInt(lastNames.length)] + firstNames[random.nextInt(firstNames.length)];
                int randomAge = ThreadLocalRandom.current().nextInt(20, 71);
                String randomName = "user" + ThreadLocalRandom.current().nextInt(1000, 10000);
                String randomEmail = randomName + "@example.com";

                CustomerRegistDTO newCustomer = new CustomerRegistDTO();
                newCustomer.setName(name);
                newCustomer.setAge(randomAge);
                newCustomer.setPhone(randomPhone);
                newCustomer.setEmergePhone(randomPhone);
                newCustomer.setEmail(randomEmail);
                newCustomer.setSex(sex);
                newCustomer.setMemberId(memberId);
                customerCommandService.registerCustomerInfo(newCustomer);
            }
        }

        String[][] carDealershipsInfo = {
                {"서울 중앙 영업점", "서울특별시 중구 세종대로 123"},
                {"부산 서면 영업점", "부산광역시 부산진구 중앙대로 456"},
                {"인천 남동구 영업점", "인천광역시 남동구 인주대로 789"},
                {"대전 둔산 영업점", "대전광역시 서구 둔산로 101"},
                {"광주 광산구 영업점", "광주광역시 광산구 상무대로 202"},
                {"대구 동구 영업점", "대구광역시 동구 아양로 303"},
                {"울산 중구 영업점", "울산광역시 중구 태화로 404"},
                {"수원 영통구 영업점", "경기도 수원시 영통구 매탄로 505"},
                {"성남 분당 영업점", "경기도 성남시 분당구 정자일로 606"},
                {"제주 서귀포 영업점", "제주특별자치도 서귀포시 태평로 707"}
        };

        // Center 매장 등록
        CenterRegistDTO newCenter1 = new CenterRegistDTO("서울 중앙 영업점", "서울특별시 중구 세종대로 123", "02-123-4567", 25, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter1, loadImage("default.png"));
        CenterRegistDTO newCenter2 = new CenterRegistDTO("부산 서면 영업점", "부산광역시 부산진구 중앙대로 456", "051-123-4567", 20, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter2, loadImage("default.png"));
        CenterRegistDTO newCenter3 = new CenterRegistDTO("인천 남동구 영업점", "인천광역시 남동구 인주대로 789", "032-123-4567", 18, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter3, loadImage("default.png"));
        CenterRegistDTO newCenter4 = new CenterRegistDTO("대전 둔산 영업점", "대전광역시 서구 둔산로 101", "042-123-4567", 15, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter4, loadImage("default.png"));
        CenterRegistDTO newCenter5 = new CenterRegistDTO("광주 광산구 영업점", "광주광역시 광산구 상무대로 202", "062-123-4567", 17, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter5, loadImage("default.png"));
        CenterRegistDTO newCenter6 = new CenterRegistDTO("대구 동구 영업점", "대구광역시 동구 아양로 303", "053-123-4567", 22, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter6, loadImage("default.png"));
        CenterRegistDTO newCenter7 = new CenterRegistDTO("울산 중구 영업점", "울산광역시 중구 태화로 404", "052-123-4567", 13, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter7, loadImage("default.png"));
        CenterRegistDTO newCenter8 = new CenterRegistDTO("수원 영통구 영업점", "경기도 수원시 영통구 매탄로 505", "031-123-4567", 19, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter8, loadImage("default.png"));
        CenterRegistDTO newCenter9 = new CenterRegistDTO("성남 분당 영업점", "경기도 성남시 분당구 정자일로 606", "031-234-5678", 21, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter9, loadImage("default.png"));
        CenterRegistDTO newCenter10 = new CenterRegistDTO("제주 서귀포 영업점", "제주특별자치도 서귀포시 태평로 707", "064-123-4567", 12, "09:00 - 18:00", null);
        centerCommandService.registCenter(newCenter10, loadImage("default.png"));


        // Product 제품 등록
        Product newProduct1 = new Product("KIA15-S001", 20000000, "기아 소렌토 2015", 10);
        productRepository.save(newProduct1);

        Product newProduct2 = new Product("KIA15-S002", 18000000, "기아 스포티지 2015", 8);
        productRepository.save(newProduct2);

        Product newProduct3 = new Product("KIA16-S003", 22000000, "기아 소렌토 2016", 12);
        productRepository.save(newProduct3);

        Product newProduct4 = new Product("KIA16-S004", 19500000, "기아 옵티마 2016", 5);
        productRepository.save(newProduct4);

        Product newProduct5 = new Product("KIA17-S005", 25000000, "기아 스팅어 2017", 7);
        productRepository.save(newProduct5);

        Product newProduct6 = new Product("KIA17-S006", 21000000, "기아 스포티지 2017", 9);
        productRepository.save(newProduct6);

        Product newProduct7 = new Product("KIA18-S007", 23000000, "기아 소렌토 2018", 6);
        productRepository.save(newProduct7);

        Product newProduct8 = new Product("KIA18-S008", 20000000, "기아 리오 2018", 11);
        productRepository.save(newProduct8);

        Product newProduct9 = new Product("KIA19-S009", 27000000, "기아 스팅어 2019", 4);
        productRepository.save(newProduct9);

        Product newProduct10 = new Product("KIA19-S010", 21500000, "기아 포르테 2019", 3);
        productRepository.save(newProduct10);

        Product newProduct11 = new Product("KIA20-S011", 29000000, "기아 텔루라이드 2020", 10);
        productRepository.save(newProduct11);

        Product newProduct12 = new Product("KIA20-S012", 25000000, "기아 옵티마 2020", 7);
        productRepository.save(newProduct12);

        Product newProduct13 = new Product("KIA21-S013", 30000000, "기아 소렌토 2021", 12);
        productRepository.save(newProduct13);

        Product newProduct14 = new Product("KIA21-S014", 27000000, "기아 카니발 2021", 5);
        productRepository.save(newProduct14);

        Product newProduct15 = new Product("KIA22-S015", 32000000, "기아 EV6 2022", 6);
        productRepository.save(newProduct15);

        Product newProduct16 = new Product("KIA22-S016", 28000000, "기아 셀토스 2022", 8);
        productRepository.save(newProduct16);

        Product newProduct17 = new Product("KIA23-S017", 33000000, "기아 소렌토 2023", 9);
        productRepository.save(newProduct17);

        Product newProduct18 = new Product("KIA23-S018", 29000000, "기아 스포티지 2023", 4);
        productRepository.save(newProduct18);

        Product newProduct19 = new Product("KIA24-S019", 35000000, "기아 EV9 2024", 3);
        productRepository.save(newProduct19);

        Product newProduct20 = new Product("KIA24-S020", 31000000, "기아 스팅어 2024", 5);
        productRepository.save(newProduct20);

        Product newProduct21 = new Product("KIA15-S021", 17000000, "기아 리오 2015", 6);
        productRepository.save(newProduct21);

        Product newProduct22 = new Product("KIA16-S022", 21000000, "기아 포르테 2016", 4);
        productRepository.save(newProduct22);

        Product newProduct23 = new Product("KIA17-S023", 24000000, "기아 옵티마 2017", 8);
        productRepository.save(newProduct23);

        Product newProduct24 = new Product("KIA18-S024", 22500000, "기아 소울 2018", 7);
        productRepository.save(newProduct24);

        Product newProduct25 = new Product("KIA19-S025", 28000000, "기아 텔루라이드 2019", 5);
        productRepository.save(newProduct25);

        Product newProduct26 = new Product("KIA20-S026", 26000000, "기아 니로 2020", 9);
        productRepository.save(newProduct26);

        Product newProduct27 = new Product("KIA21-S027", 29500000, "기아 카니발 2021", 3);
        productRepository.save(newProduct27);

        Product newProduct28 = new Product("KIA22-S028", 31000000, "기아 소렌토 2022", 10);
        productRepository.save(newProduct28);

        Product newProduct29 = new Product("KIA23-S029", 34000000, "기아 EV6 2023", 6);
        productRepository.save(newProduct29);

        Product newProduct30 = new Product("KIA24-S030", 32000000, "기아 셀토스 2024", 4);
        productRepository.save(newProduct30);

        Product newProduct31 = new Product("KIA15-S031", 18500000, "기아 소울 2015", 7);
        productRepository.save(newProduct31);

        Product newProduct32 = new Product("KIA16-S032", 22000000, "기아 소렌토 2016", 6);
        productRepository.save(newProduct32);

        Product newProduct33 = new Product("KIA17-S033", 25000000, "기아 포르테 2017", 5);
        productRepository.save(newProduct33);

        Product newProduct34 = new Product("KIA18-S034", 23000000, "기아 리오 2018", 8);
        productRepository.save(newProduct34);

        Product newProduct35 = new Product("KIA19-S035", 27000000, "기아 스포티지 2019", 4);
        productRepository.save(newProduct35);

        Product newProduct36 = new Product("KIA20-S036", 29500000, "기아 텔루라이드 2020", 9);
        productRepository.save(newProduct36);

        Product newProduct37 = new Product("KIA21-S037", 28000000, "기아 셀토스 2021", 6);
        productRepository.save(newProduct37);

        Product newProduct38 = new Product("KIA22-S038", 31500000, "기아 스포티지 2022", 10);
        productRepository.save(newProduct38);

        Product newProduct39 = new Product("KIA23-S039", 33000000, "기아 스팅어 2023", 7);
        productRepository.save(newProduct39);

        Product newProduct40 = new Product("KIA24-S040", 35500000, "기아 EV9 2024", 3);
        productRepository.save(newProduct40);

        Product newProduct41 = new Product("KIA15-S041", 19500000, "기아 옵티마 2015", 6);
        productRepository.save(newProduct41);

        Product newProduct42 = new Product("KIA16-S042", 22500000, "기아 소울 2016", 5);
        productRepository.save(newProduct42);

        Product newProduct43 = new Product("KIA17-S043", 25500000, "기아 니로 2017", 9);
        productRepository.save(newProduct43);

        Product newProduct44 = new Product("KIA18-S044", 24000000, "기아 포르테 2018", 4);
        productRepository.save(newProduct44);

        Product newProduct45 = new Product("KIA19-S045", 28500000, "기아 텔루라이드 2019", 8);
        productRepository.save(newProduct45);

        Product newProduct46 = new Product("KIA20-S046", 26500000, "기아 소렌토 2020", 7);
        productRepository.save(newProduct46);

        Product newProduct47 = new Product("KIA21-S047", 30000000, "기아 스팅어 2021", 3);
        productRepository.save(newProduct47);

        Product newProduct48 = new Product("KIA22-S048", 32500000, "기아 EV6 2022", 6);
        productRepository.save(newProduct48);

        Product newProduct49 = new Product("KIA23-S049", 34000000, "기아 카니발 2023", 10);
        productRepository.save(newProduct49);

        Product newProduct50 = new Product("KIA24-S050", 36000000, "기아 EV9 2024", 5);
        productRepository.save(newProduct50);

        // 일정 저장
        // 2024년 9월
        ScheduleRegistDTO newSchedule1 = new ScheduleRegistDTO("자동차 영업 정책 교육", "영업 전략 및 판매 방향성 교육", "TRAINING", "2024-09-04 10:00:00", "2024-09-06 12:30:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule1);
        ScheduleRegistDTO newSchedule2 = new ScheduleRegistDTO("기우석 고객님 미팅", "신형 ME25 차량 견적 관련 미팅", "MEETING", "2024-09-10 14:00:00", "2024-09-10 16:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule2);
        ScheduleRegistDTO newSchedule3 = new ScheduleRegistDTO("팀별 정기 회의", "사내 불편사항 및 개선점 여부 회의", "SESSION", "2024-09-15 09:00:00", "2024-09-15 11:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule3);

// 2024년 10월
        ScheduleRegistDTO newSchedule4 = new ScheduleRegistDTO("영업 전략 워크샵", "판매 방법론 교육 일정 (서초)", "TRAINING", "2024-10-16 10:00:00", "2024-10-18 18:30:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule4);
        ScheduleRegistDTO newSchedule5 = new ScheduleRegistDTO("휴가", "가족과 함께하는 휴가", "VACATION", "2024-10-07 00:00:00", "2024-10-10 23:59:59", "god", null);
        scheduleCommandService.registSchedule(newSchedule5);
        ScheduleRegistDTO newSchedule6 = new ScheduleRegistDTO("김민석 고객님 미팅", "KM7 차량 견적 상담 미팅", "MEETING", "2024-10-14 13:00:00", "2024-10-14 15:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule6);

// 2024년 11월
        ScheduleRegistDTO newSchedule7 = new ScheduleRegistDTO("영업 기술 교육", "상담 스킬 강화 교육", "TRAINING", "2024-11-05 10:00:00", "2024-11-05 12:30:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule7);
        ScheduleRegistDTO newSchedule8 = new ScheduleRegistDTO("내부 회의", "성과 리뷰 회의", "SESSION", "2024-11-10 09:00:00", "2024-11-10 11:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule8);
        ScheduleRegistDTO newSchedule9 = new ScheduleRegistDTO("고객 미팅", "중요 고객과의 미팅", "MEETING", "2024-11-15 14:00:00", "2024-11-15 16:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule9);

// 2024년 12월
        ScheduleRegistDTO newSchedule10 = new ScheduleRegistDTO("영업 전략 교육", "판매 방법론 및 전략 교육", "TRAINING", "2024-12-02 10:00:00", "2024-12-02 12:30:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule10);
        ScheduleRegistDTO newSchedule11 = new ScheduleRegistDTO("휴가", "연말 휴가", "VACATION", "2024-12-10 00:00:00", "2024-12-12 23:59:59", "god", null);
        scheduleCommandService.registSchedule(newSchedule11);
        ScheduleRegistDTO newSchedule12 = new ScheduleRegistDTO("내부 회의", "팀워크 강화 미팅", "SESSION", "2024-12-20 10:00:00", "2024-12-20 12:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule12);

// 2025년 1월
        ScheduleRegistDTO newSchedule13 = new ScheduleRegistDTO("고객 미팅", "새해 첫 고객 미팅", "MEETING", "2025-01-05 13:00:00", "2025-01-05 15:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule13);
        ScheduleRegistDTO newSchedule14 = new ScheduleRegistDTO("영업 정책 교육", "새로운 영업 정책 교육", "TRAINING", "2025-01-10 10:00:00", "2025-01-10 12:30:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule14);
        ScheduleRegistDTO newSchedule15 = new ScheduleRegistDTO("내부 회의", "전략 회의", "SESSION", "2025-01-15 09:00:00", "2025-01-15 11:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule15);

// 2025년 2월
        ScheduleRegistDTO newSchedule16 = new ScheduleRegistDTO("고객 미팅", "고객 니즈 분석 미팅", "MEETING", "2025-02-03 13:00:00", "2025-02-03 15:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule16);
        ScheduleRegistDTO newSchedule17 = new ScheduleRegistDTO("휴가", "겨울 휴가", "VACATION", "2025-02-07 00:00:00", "2025-02-10 23:59:59", "god", null);
        scheduleCommandService.registSchedule(newSchedule17);
        ScheduleRegistDTO newSchedule18 = new ScheduleRegistDTO("영업 회의", "전략 회의 및 피드백", "SESSION", "2025-02-15 10:00:00", "2025-02-15 12:00:00", "god", null);
        scheduleCommandService.registSchedule(newSchedule18);

        // 제품 옵션 등록
        ProductOption newProductOption1 = new ProductOption('K', 'N', 'A', 'A', 'L', '4', '2', 'A', 'P', 'A', 'U', '1', '0', '1', 'B', 'W', '1', '1', '1', '1', '1', '0', '1', '1', '1');
        productOptionRepository.save(newProductOption1);

        ProductOption newProductOption2 = new ProductOption('K', 'N', 'H', 'B', 'L', '4', '4', 'B', 'R', 'B', 'Z', '1', '1', '0', 'G', 'B', '0', '1', '0', '1', '0', '1', '0', '0', '1');
        productOptionRepository.save(newProductOption2);


        ProductOption newProductOption3 = new ProductOption('K', 'M', 'H', 'C', 'M', '6', '3', 'C', 'P', 'C', 'A', '0', '1', '1', 'R', 'G', '1', '1', '1', '0', '1', '1', '0', '1', '0');
        productOptionRepository.save(newProductOption3);

        ProductOption newProductOption4 = new ProductOption('K', 'N', 'J', 'D', 'L', '2', '4', 'A', 'R', 'D', 'C', '1', '0', '0', 'B', 'R', '0', '0', '0', '1', '0', '0', '1', '0', '1');
        productOptionRepository.save(newProductOption4);

        ProductOption newProductOption5 = new ProductOption('K', 'M', 'F', 'B', 'S', '5', '2', 'D', 'O', 'B', 'A', '1', '1', '1', 'Y', 'S', '0', '1', '1', '1', '0', '0', '0', '1', '0');
        productOptionRepository.save(newProductOption5);

        ProductOption newProductOption6 = new ProductOption('K', 'N', 'A', 'F', 'L', '3', '3', 'F', 'B', 'Y', 'S', '2', '0', '1', 'T', 'B', '1', '1', '0', '0', '0', '1', '1', '1', '1');
        productOptionRepository.save(newProductOption6);

        ProductOption newProductOption7 = new ProductOption('K', 'M', 'F', 'A', 'J', '4', '2', 'D', 'S', 'C', 'V', '1', '0', '0', 'A', 'Q', '1', '1', '1', '0', '0', '0', '1', '0', '1');
        productOptionRepository.save(newProductOption7);

        ProductOption newProductOption8 = new ProductOption('K', 'N', 'A', 'B', 'F', '5', '4', 'S', 'M', 'A', 'R', '0', '1', '0', 'T', 'S', '1', '1', '1', '0', '0', '1', '0', '1', '1');
        productOptionRepository.save(newProductOption8);

        ProductOption newProductOption9 = new ProductOption('K', 'M', 'B', 'R', 'K', '2', '3', 'C', 'F', 'G', 'W', '1', '0', '0', 'A', 'F', '1', '1', '0', '1', '0', '0', '1', '0', '1');
        productOptionRepository.save(newProductOption9);

        ProductOption newProductOption10 = new ProductOption('K', 'N', 'F', 'Y', 'L', '4', '3', 'B', 'Y', 'C', 'Z', '1', '1', '1', 'X', 'S', '0', '1', '0', '0', '1', '1', '1', '0', '1');
        productOptionRepository.save(newProductOption10);

        ProductOption newProductOption11 = new ProductOption('K', 'M', 'E', 'Z', 'S', '3', '2', 'F', 'M', 'A', 'B', '0', '1', '0', 'A', 'N', '1', '0', '1', '0', '0', '1', '1', '0', '1');
        productOptionRepository.save(newProductOption11);

        ProductOption newProductOption12 = new ProductOption('K', 'N', 'A', 'F', 'J', '2', '3', 'F', 'R', 'B', 'S', '1', '0', '1', 'Y', 'G', '1', '1', '0', '1', '1', '1', '0', '0', '0');
        productOptionRepository.save(newProductOption12);

        ProductOption newProductOption13 = new ProductOption('K', 'M', 'D', 'T', 'K', '5', '4', 'S', 'A', 'V', 'L', '0', '1', '1', 'B', 'M', '0', '1', '1', '0', '0', '0', '1', '1', '1');
        productOptionRepository.save(newProductOption13);

        ProductOption newProductOption14 = new ProductOption('K', 'M', 'A', 'Z', 'P', '4', '2', 'D', 'P', 'S', 'B', '1', '1', '1', 'Y', 'Q', '1', '0', '0', '1', '1', '0', '0', '0', '1');
        productOptionRepository.save(newProductOption14);

        ProductOption newProductOption15 = new ProductOption('K', 'M', 'F', 'W', 'J', '6', '3', 'B', 'R', 'V', 'Z', '0', '0', '1', 'B', 'R', '1', '1', '0', '0', '1', '1', '0', '1', '0');
        productOptionRepository.save(newProductOption15);

        ProductOption newProductOption16 = new ProductOption('K', 'N', 'A', 'C', 'L', '3', '5', 'S', 'Q', 'M', 'R', '1', '1', '0', 'Y', 'B', '0', '1', '1', '0', '0', '0', '0', '0', '1');
        productOptionRepository.save(newProductOption16);

        ProductOption newProductOption17 = new ProductOption('K', 'M', 'F', 'A', 'J', '4', '2', 'D', 'S', 'C', 'V', '1', '0', '1', 'A', 'Q', '1', '1', '0', '0', '1', '0', '1', '1', '0');
        productOptionRepository.save(newProductOption17);

        ProductOption newProductOption18 = new ProductOption('K', 'N', 'F', 'B', 'S', '3', '3', 'S', 'M', 'A', 'R', '0', '1', '0', 'T', 'S', '1', '1', '0', '1', '0', '1', '0', '1', '1');
        productOptionRepository.save(newProductOption18);

        ProductOption newProductOption19 = new ProductOption('K', 'M', 'F', 'C', 'T', '5', '3', 'F', 'Q', 'C', 'Z', '0', '1', '1', 'B', 'R', '0', '1', '1', '1', '0', '1', '0', '0', '1');
        productOptionRepository.save(newProductOption19);

        ProductOption newProductOption20 = new ProductOption('K', 'N', 'F', 'A', 'P', '4', '4', 'D', 'P', 'S', 'B', '1', '0', '0', 'Y', 'Q', '0', '1', '0', '1', '0', '0', '1', '1', '0');
        productOptionRepository.save(newProductOption20);

        ProductOption newProductOption21 = new ProductOption('K', 'M', 'F', 'B', 'L', '3', '2', 'F', 'R', 'M', 'A', '1', '0', '1', 'G', 'Y', '0', '1', '0', '1', '1', '0', '0', '1', '1');
        productOptionRepository.save(newProductOption21);

        ProductOption newProductOption22 = new ProductOption('K', 'N', 'F', 'A', 'J', '5', '3', 'D', 'S', 'C', 'W', '0', '1', '1', 'A', 'F', '1', '0', '1', '1', '0', '0', '0', '0', '1');
        productOptionRepository.save(newProductOption22);

        ProductOption newProductOption23 = new ProductOption('K', 'M', 'F', 'C', 'S', '4', '3', 'B', 'Q', 'A', 'Z', '1', '0', '0', 'Y', 'R', '1', '0', '1', '0', '1', '1', '1', '0', '0');
        productOptionRepository.save(newProductOption23);

        ProductOption newProductOption24 = new ProductOption('K', 'N', 'F', 'D', 'T', '2', '4', 'F', 'P', 'B', 'A', '1', '1', '0', 'Y', 'Q', '0', '1', '1', '1', '0', '1', '1', '0', '1');
        productOptionRepository.save(newProductOption24);

        ProductOption newProductOption25 = new ProductOption('K', 'M', 'F', 'B', 'J', '3', '5', 'S', 'M', 'Z', 'Y', '0', '0', '1', 'A', 'T', '1', '1', '0', '1', '0', '0', '0', '0', '1');
        productOptionRepository.save(newProductOption25);

        ProductOption newProductOption26 = new ProductOption('K', 'N', 'F', 'A', 'L', '4', '2', 'D', 'S', 'C', 'V', '1', '0', '1', 'G', 'Y', '0', '1', '1', '1', '1', '1', '0', '0', '1');
        productOptionRepository.save(newProductOption26);

        ProductOption newProductOption27 = new ProductOption('K', 'M', 'F', 'C', 'S', '2', '3', 'F', 'Q', 'B', 'A', '0', '1', '0', 'T', 'R', '0', '1', '1', '0', '0', '1', '1', '1', '0');
        productOptionRepository.save(newProductOption27);

        ProductOption newProductOption28 = new ProductOption('K', 'N', 'F', 'B', 'M', '5', '4', 'B', 'R', 'D', 'S', '1', '0', '1', 'X', 'Q', '1', '1', '0', '0', '1', '0', '0', '1', '1');
        productOptionRepository.save(newProductOption28);

        ProductOption newProductOption29 = new ProductOption('K', 'M', 'F', 'A', 'J', '3', '2', 'C', 'P', 'Z', 'V', '0', '1', '1', 'G', 'S', '1', '0', '0', '1', '0', '1', '0', '1', '0');
        productOptionRepository.save(newProductOption29);

        ProductOption newProductOption30 = new ProductOption('K', 'N', 'F', 'C', 'L', '6', '4', 'S', 'Q', 'M', 'Z', '1', '0', '0', 'A', 'T', '1', '1', '0', '1', '1', '0', '0', '1', '1');
        productOptionRepository.save(newProductOption30);

        ProductOption newProductOption31 = new ProductOption('K', 'M', 'F', 'A', 'S', '2', '3', 'F', 'M', 'B', 'R', '0', '1', '1', 'Y', 'Q', '1', '1', '0', '0', '1', '0', '1', '1', '0');
        productOptionRepository.save(newProductOption31);

        ProductOption newProductOption32 = new ProductOption('K', 'N', 'F', 'B', 'P', '4', '2', 'S', 'R', 'A', 'Q', '1', '0', '0', 'T', 'S', '0', '1', '1', '1', '0', '0', '1', '1', '0');
        productOptionRepository.save(newProductOption32);

        ProductOption newProductOption33 = new ProductOption('K', 'M', 'F', 'C', 'L', '3', '5', 'B', 'S', 'V', 'Z', '1', '0', '1', 'A', 'R', '1', '0', '0', '0', '1', '0', '0', '1', '0');
        productOptionRepository.save(newProductOption33);

        ProductOption newProductOption34 = new ProductOption('K', 'N', 'F', 'A', 'J', '2', '3', 'D', 'P', 'W', 'R', '0', '1', '0', 'G', 'Y', '1', '0', '1', '1', '0', '1', '0', '1', '0');
        productOptionRepository.save(newProductOption34);

        ProductOption newProductOption35 = new ProductOption('K', 'M', 'F', 'B', 'S', '4', '2', 'C', 'F', 'Z', 'V', '1', '0', '0', 'T', 'R', '0', '1', '0', '1', '0', '1', '0', '1', '1');
        productOptionRepository.save(newProductOption35);

        ProductOption newProductOption36 = new ProductOption('K', 'N', 'F', 'C', 'L', '3', '3', 'S', 'Q', 'M', 'Y', '1', '1', '0', 'A', 'T', '1', '0', '0', '1', '0', '1', '1', '1', '0');
        productOptionRepository.save(newProductOption36);

        ProductOption newProductOption37 = new ProductOption('K', 'M', 'F', 'A', 'T', '5', '4', 'F', 'P', 'S', 'A', '0', '1', '1', 'G', 'R', '1', '1', '0', '1', '0', '1', '0', '0', '0');
        productOptionRepository.save(newProductOption37);

        ProductOption newProductOption38 = new ProductOption('K', 'N', 'F', 'B', 'L', '2', '3', 'C', 'M', 'B', 'Z', '1', '1', '0', 'Y', 'P', '0', '0', '1', '1', '1', '1', '0', '0', '0');
        productOptionRepository.save(newProductOption38);

        ProductOption newProductOption39 = new ProductOption('K', 'M', 'F', 'C', 'S', '4', '2', 'D', 'Q', 'A', 'R', '1', '0', '1', 'T', 'Y', '0', '1', '0', '1', '1', '0', '0', '0', '1');
        productOptionRepository.save(newProductOption39);

        ProductOption newProductOption40 = new ProductOption('K', 'N', 'F', 'A', 'J', '3', '5', 'S', 'M', 'B', 'A', '0', '1', '0', 'Y', 'S', '1', '0', '1', '1', '1', '0', '0', '1', '0');
        productOptionRepository.save(newProductOption40);

        ProductOption newProductOption41 = new ProductOption('K', 'M', 'F', 'B', 'T', '5', '3', 'D', 'F', 'S', 'Q', '1', '1', '0', 'A', 'M', '0', '1', '0', '0', '1', '1', '1', '0', '1');
        productOptionRepository.save(newProductOption41);

        ProductOption newProductOption42 = new ProductOption('K', 'N', 'F', 'C', 'L', '4', '2', 'B', 'R', 'M', 'Z', '1', '0', '1', 'Y', 'S', '0', '1', '1', '1', '0', '1', '0', '1', '0');
        productOptionRepository.save(newProductOption42);

        ProductOption newProductOption43 = new ProductOption('K', 'M', 'F', 'A', 'S', '3', '5', 'S', 'Q', 'B', 'C', '0', '1', '1', 'G', 'P', '1', '0', '0', '1', '1', '0', '0', '1', '0');
        productOptionRepository.save(newProductOption43);

        ProductOption newProductOption44 = new ProductOption('K', 'N', 'F', 'B', 'M', '6', '4', 'F', 'R', 'L', 'Y', '1', '0', '1', 'B', 'T', '0', '1', '1', '1', '0', '0', '1', '1', '0');
        productOptionRepository.save(newProductOption44);

        ProductOption newProductOption45 = new ProductOption('K', 'M', 'F', 'C', 'L', '5', '3', 'D', 'M', 'B', 'R', '0', '1', '0', 'Y', 'P', '1', '0', '1', '1', '1', '1', '0', '1', '0');
        productOptionRepository.save(newProductOption45);

        ProductOption newProductOption46 = new ProductOption('K', 'N', 'F', 'A', 'T', '4', '2', 'S', 'Q', 'P', 'W', '0', '1', '1', 'T', 'S', '1', '1', '0', '1', '0', '0', '0', '0', '1');
        productOptionRepository.save(newProductOption46);

        ProductOption newProductOption47 = new ProductOption('K', 'M', 'F', 'B', 'J', '3', '5', 'C', 'M', 'Z', 'R', '0', '1', '0', 'Y', 'Q', '1', '0', '1', '0', '0', '0', '1', '1', '1');
        productOptionRepository.save(newProductOption47);

        ProductOption newProductOption48 = new ProductOption('K', 'N', 'F', 'C', 'S', '5', '3', 'D', 'F', 'P', 'A', '1', '0', '1', 'A', 'T', '0', '1', '0', '1', '0', '1', '1', '0', '0');
        productOptionRepository.save(newProductOption48);

        ProductOption newProductOption49 = new ProductOption('K', 'M', 'F', 'A', 'R', '2', '4', 'C', 'Q', 'B', 'M', '1', '0', '1', 'Y', 'P', '0', '1', '0', '0', '1', '1', '0', '0', '1');
        productOptionRepository.save(newProductOption49);

        ProductOption newProductOption50 = new ProductOption('K', 'M', 'F', 'L', 'P', '3', '4', 'C', 'F', 'A', 'R', '1', '0', '1', 'G', 'Y', '0', '1', '0', '1', '1', '0', '0', '1', '0');
        productOptionRepository.save(newProductOption50);



    }

    private String getRandomEmploymentDate() {
        Random random = new Random();

        // Year range: 2015 - 2021
        int year = 2015 + random.nextInt(7);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);

        LocalDate employmentDate = LocalDate.of(year, month, day);
        return employmentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String getRandomResignationDate(LocalDate employmentDate) {
        Random random = new Random();

        // Maximum duration for resignation: 0 to 365 days after employment
        int daysToAdd = random.nextInt(366); // Add between 0 and 365 days

        LocalDate resignationDate = employmentDate.plusDays(daysToAdd);
        return resignationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
