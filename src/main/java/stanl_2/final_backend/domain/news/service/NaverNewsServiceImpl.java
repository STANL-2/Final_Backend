package stanl_2.final_backend.domain.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import stanl_2.final_backend.domain.news.dto.NaverNewsDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class NaverNewsServiceImpl implements NaverNewsService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Autowired
    public NaverNewsServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public List<NaverNewsDTO> getNews() throws JsonProcessingException {
        // 네이버 뉴스 검색 URL
        String query = "자동차";
        String apiUrl = "https://openapi.naver.com/v1/search/news.json?query=" + query + "&display=10";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출
        String response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class).getBody();

        // ObjectMapper를 사용하여 JSON을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        // "items" 배열을 NaverNewsDTO 리스트로 변환
        List<NaverNewsDTO> result = new ArrayList<>();
        for (JsonNode itemNode : jsonNode.path("items")) {
            NaverNewsDTO naverNewsDTO = objectMapper.treeToValue(itemNode, NaverNewsDTO.class);
            result.add(naverNewsDTO);
        }

        return result;
    }

}
