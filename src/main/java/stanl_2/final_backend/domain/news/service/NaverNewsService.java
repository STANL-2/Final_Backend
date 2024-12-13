package stanl_2.final_backend.domain.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import stanl_2.final_backend.domain.news.dto.NaverNewsDTO;

import java.util.List;

public interface NaverNewsService {
    List<NaverNewsDTO> getNews() throws JsonProcessingException;
}
