package stanl_2.final_backend.domain.problem.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.query.dto.NoticeExcelDownload;
import stanl_2.final_backend.domain.problem.query.dto.ProblemDTO;
import stanl_2.final_backend.domain.problem.query.dto.ProblemExcelDownload;
import stanl_2.final_backend.domain.problem.query.dto.ProblemSearchDTO;
import stanl_2.final_backend.domain.problem.query.repository.ProblemMapper;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import stanl_2.final_backend.global.redis.RedisService;

import java.util.List;

@Service("queryProblemServiceImpl")
public class ProblemServiceImpl implements ProblemService {
    private final ProblemMapper problemMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;
    private final ExcelUtilsV1 excelUtilsV1;
    private final MemberQueryService memberQueryService;

    @Autowired
    public ProblemServiceImpl(ProblemMapper problemMapper, RedisTemplate<String, Object> redisTemplate, RedisService redisService, ExcelUtilsV1 excelUtilsV1, MemberQueryService memberQueryService) {
        this.problemMapper = problemMapper;
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
        this.excelUtilsV1 =excelUtilsV1;
        this.memberQueryService =memberQueryService;
    }

    @Transactional
    @Override
    public Page<ProblemDTO> findProblems(Pageable pageable, ProblemSearchDTO problemSearchDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        String cacheKey = "ProblemCache::offset=" + offset + "::size=" + size +"::title="+problemSearchDTO.getTitle() +
        "::memberId="+problemSearchDTO.getMemberId()+"::productId="+problemSearchDTO.getProductId()+
        "::customerId="+problemSearchDTO.getCustomerId()+"::startDate"+problemSearchDTO.getStartDate()+
        "::endDate"+problemSearchDTO.getEndDate();
        // Redis에서 데이터 조회
        List<ProblemDTO> problems = (List<ProblemDTO>) redisTemplate.opsForValue().get(cacheKey);

        if (problems == null) {
            System.out.println("데이터베이스에서 문제 데이터 조회 중...");
            problems = problemMapper.findProblems(offset, size, problemSearchDTO);
            if (problems != null && !problems.isEmpty())
                redisService.setKeyWithTTL(cacheKey, problems, 30 * 60);
        } else {
            System.out.println("캐시에서 문제 데이터 조회 중...");
        }
        problems.forEach(problem -> {
            try {
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
        });
        Integer totalElements = problemMapper.findProblemsCount(problemSearchDTO);
        return new PageImpl<>(problems, pageable, totalElements);
    }

    @Transactional
    @Override
    public ProblemDTO findProblem(String problemId) {
        ProblemDTO problemDTO = problemMapper.findProblem(problemId);
        return problemDTO;
    }
    @Transactional
    @Override
    public void exportProblemsToExcel(HttpServletResponse response) {
        List<ProblemExcelDownload> problemList = problemMapper.findProblemsForExcel();

        excelUtilsV1.download(ProblemExcelDownload.class, problemList, "problemExcel", response);
    }
}
