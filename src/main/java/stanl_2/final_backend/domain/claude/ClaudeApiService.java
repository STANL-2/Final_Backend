package stanl_2.final_backend.domain.claude;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClaudeApiService {

    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ClaudeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSummary(String comment) {

        String requestBody = String.format("{\"text\": \"%s\"}", comment);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();  // Return the summary text
            } else {
                // 외부 API에서 오류가 발생한 경우
                return "Error: " + response.getStatusCode() + " - " + response.getBody();
            }
        } catch (Exception e) {
            // 예외 처리: API 호출 실패
            return "Error: Unable to connect to Claude API. " + e.getMessage();
        }
    }
}
