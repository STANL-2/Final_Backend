package stanl_2.final_backend.domain.news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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
