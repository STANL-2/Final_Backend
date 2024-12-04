package stanl_2.final_backend.domain.news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import stanl_2.final_backend.domain.member.common.response.MemberResponseMessage;
import stanl_2.final_backend.domain.news.common.response.NewsResponseMessage;
import stanl_2.final_backend.domain.news.dto.NaverNewsDTO;
import stanl_2.final_backend.domain.news.service.NaverNewsService;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
public class NaverNewsController {

    private final NaverNewsService naverNewsService;

    @Autowired
    public NaverNewsController(NaverNewsService naverNewsService) {
        this.naverNewsService = naverNewsService;
    }

    @Operation(summary = "자동차 뉴스 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/car")
    public ResponseEntity<NewsResponseMessage> getCarNews() throws JsonProcessingException {

        List<NaverNewsDTO> result = naverNewsService.getNews();

        return ResponseEntity.ok(NewsResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .msg("성공")
                                                    .result(result)
                                                    .build());
    }
}
