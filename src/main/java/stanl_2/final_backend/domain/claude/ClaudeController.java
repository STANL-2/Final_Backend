package stanl_2.final_backend.domain.claude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.claude.dto.RequestDTO;

@RestController
@RequestMapping("/api/v1/claude")
public class ClaudeController {

    private final ClaudeApiService claudeApiService;

    @Autowired
    public ClaudeController(ClaudeApiService claudeApiService) {
        this.claudeApiService = claudeApiService;
    }

    @PostMapping("summary")
    public ResponseEntity<?> getSummary(@RequestBody RequestDTO requestDTO) {

        try {
            String summary = claudeApiService.getSummary(requestDTO.getComment());
            if (summary != null) {
                return ResponseEntity.ok(summary);
            } else {
                return ResponseEntity.status(500).body("Failed to get summary.");
            }
        } catch (Exception e) {
            // 로깅 및 예외 처리
            return ResponseEntity.status(500).body("An error occurred while processing your request: " + e.getMessage());
        }
    }
}
